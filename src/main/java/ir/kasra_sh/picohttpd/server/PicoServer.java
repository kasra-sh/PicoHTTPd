package ir.kasra_sh.picohttpd.server;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.server.interfaces.*;
import ir.kasra_sh.picohttpd.server.nio.RequestParser;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;
import ir.kasra_sh.picohttpd.server.tasks.AcceptTask;
import ir.kasra_sh.picohttpd.server.tasks.ReadTask;
import ir.kasra_sh.picohttpd.server.nio.NIOSocketReader;
import ir.kasra_sh.picohttpd.server.tasks.WriteTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

class PicoServer implements Runnable, ReadListener, AcceptListener, WriteFinishListener, WriteListener, FailListener {
    private ServerSocketChannel server;
    private int port;
    private Executor executor;

    private ReadTask readTask = new ReadTask(this);
    private AcceptTask acceptTask;
    private WriteTask writeTask;

    private HTTPHandler handler = new HTTPHandler() {
        @Override
        public void handleRequest(Request r, ResponseWriter w) {
            w.write(("HTTP/1.1 200 OK\r\nContent-Length: 10\r\n\r\n" +
                    "123456789\n").getBytes());
        }
    };

    @Override
    public void onFail(SocketChannel s) {

    }

    private class HRun implements Runnable {

        private HTTPHandler httpHandler;
        private Request r;
        private ResponseWriter rw;

        public HRun set(HTTPHandler httpHandler, Request r, ResponseWriter w) {
            this.httpHandler = httpHandler;
            this.r = r;
            this.rw = w;
            return this;
        }

        @Override
        public void run() {
            httpHandler.handleRequest(r, rw);
        }
    }

    private HRun runner = new HRun();

    public void setHandler(HTTPHandler handler) {
        this.handler = handler;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public PicoServer(int port) throws IOException {
        this.port = port;
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        acceptTask = new AcceptTask(this, server);
        writeTask = new WriteTask(this);
    }

    @Override
    public void run() {
        init();
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        Thread readThread = new Thread(readTask);
        readThread.setName("NanoReadThread");
        Thread acceptThread = new Thread(acceptTask);
        acceptThread.setName("NanoAcceptThread");
        Thread writeThread = new Thread(writeTask);
        writeThread.setName("NanoWriteThread");

        acceptThread.start();
        readThread.start();
        writeThread.start();
    }

    ///////////////

    long acc = 0;
    @Override
    public void onAccept(SocketChannel socketChannel) {
        if (socketChannel != null) {
            try {
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            acc++;
            readTask.addSocket(
                    socketChannel,
                    new RequestParser(new NIOSocketReader(socketChannel))
            );
        }
    }

    @Override
    public void onReadFinished(SocketChannel socketChannel, final RequestParser parser) {
        final ResponseWriter rw = new ResponseWriter(this, socketChannel);
        if (executor != null) {
            try {
                parser.req().setAddress(socketChannel.getRemoteAddress());
            } catch (IOException e) { }
            executor.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            handler.handleRequest(parser.req(), rw);
                        }
                    }
            );
        }
        else {
            try {
                parser.req().setAddress(socketChannel.getRemoteAddress());
            } catch (IOException e) { }
            handler.handleRequest(parser.req(), rw);
        }
    }


    long last = System.currentTimeMillis();
    long ftl = 0;
    float rps = 0;
    @Override
    public void onWriteFinished(SocketChannel socketChannel) {
        try {
            ftl++;
            long cur = System.currentTimeMillis();
            if (cur - last > 1000) {
//                System.out.println("is");
                rps = ((float) ftl)/((cur -last)/1000f);
                ftl = 0;
                last = System.currentTimeMillis();
            }
            socketChannel.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void onWrite(SocketChannel s, byte[] b) {
        writeTask.addWrite(s, b);
    }

    @Override
    public void onWriteStream(SocketChannel s, byte[] d, InputStream is, int len) {
        writeTask.addWrite(s, d, is, len);
    }

    public Stat getStat(){
//        System.out.println("Accepted : "+acc);
        if (System.currentTimeMillis() - last>2000) rps = 0;
        return new Stat(readTask.getAwait(), writeTask.getAwait(), rps);
    }
}
