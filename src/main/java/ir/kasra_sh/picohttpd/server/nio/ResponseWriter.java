package ir.kasra_sh.picohttpd.server.nio;

import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.server.interfaces.WriteListener;

import java.io.InputStream;
import java.nio.channels.SocketChannel;

public class ResponseWriter {

    private WriteListener listener;
    private SocketChannel socket;
    private boolean w = false;

    public ResponseWriter(WriteListener listener, SocketChannel socketChannel) {
        this.listener = listener;
        this.socket = socketChannel;
    }

    private byte[] responseToBytes(Response r){
        return r.asByteArray();
    }

    public void write(Response r){
        if (w) return;
        listener.onWrite(socket, responseToBytes(r));
        w = true;
    }
    public void write(byte[] bytes){
        if (w) return;
        listener.onWrite(socket, bytes);
        w = true;
    }

    public void writeStream(byte[] head, InputStream inputStream, int len) {
        if (w) return;
        listener.onWriteStream(socket, head, inputStream, len);
        w = true;
    }
}
