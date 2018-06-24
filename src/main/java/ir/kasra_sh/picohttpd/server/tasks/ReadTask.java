package ir.kasra_sh.picohttpd.server.tasks;

import ir.kasra_sh.picohttpd.server.nio.RequestParser;
import ir.kasra_sh.picohttpd.server.interfaces.ReadListener;
import ir.kasra_sh.picohttpd.server.tasks.models.ReadPair;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ReadTask implements Runnable {

    private ReadListener listener;
    private Selector selector = null;
    private ConcurrentLinkedQueue<ReadPair> reads = new ConcurrentLinkedQueue<>();
    private DeadWatcher watcher = new DeadWatcher();

    public void addSocket(SocketChannel sc, RequestParser parser) {
        wakeUp();
        watcher.add(sc);
        reads.add(new ReadPair(sc, parser));
    }

    public ReadTask(ReadListener listener) {
        this.listener = listener;
        try {
            selector = Selector.open();
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void run() {
        while (true) {
            while (!reads.isEmpty()) {
                ReadPair r = reads.remove();
                try {
                    if (r.getSocket().isOpen())
                        r.getSocket().register(selector, SelectionKey.OP_READ, r.getParser());
                } catch (ClosedChannelException e) { }
            }
            try {
                for (SelectionKey k: selector.keys()){
                    if (watcher.shouldRemove((SocketChannel) k.channel(), 7000)){
                        k.channel().close();
                        k.cancel();
                        watcher.remove((SocketChannel) k.channel());
                    }
                }
//                selector.selectNow();
                selector.select(1);
                Set<SelectionKey> keys = selector.selectedKeys();
                if (keys.size() > 0) {
                    doReadAll(keys);
                }else {
                }
//                cycleWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void doReadAll(Set<SelectionKey> keys) {
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey k = iterator.next();
            if (!k.isReadable()){
                k.cancel();
            }
            else
                doReadKey(k);
            iterator.remove();
        }
    }

    private void doReadKey(SelectionKey k) {
        SocketChannel sc = (SocketChannel) k.channel();
        RequestParser rp = (RequestParser) k.attachment();
        try {
            if (!sc.isOpen()) {
                watcher.remove(sc);
                k.cancel();
                return;
            }
            if (rp.readHeader()){
                doAfterReadHeader(k, sc, rp);
            }
        } catch (Exception e) {
            watcher.remove(sc);
            k.cancel();
            try {
                sc.close();
            }catch (Exception ee){}
        }
    }

    private void doAfterReadHeader(SelectionKey k, SocketChannel sc, RequestParser rp) throws IOException {
        if (rp.hasBody()) {
            if (rp.readBody()){
                k.cancel();
                doAfterReadBody(sc, rp);
            }
        } else {
            k.cancel();
            doAfterReadBody(sc, rp);
        }
    }

    private void doAfterReadBody(SocketChannel sc, RequestParser parser){
        watcher.remove(sc);
        parser.finish();
        listener.onReadFinished(sc, parser);
    }


    private long last = 0;

    private void cycleWait() {
        if (shouldWait()) {
            try {
                for (int i = 0; i < 2000; i++) {
                    if (shouldWait())
                        TimeUnit.NANOSECONDS.sleep(10);
                    else {
                        break;
                    }
//                    if (i==1999) {
//                        try {
//                            for (SelectionKey k: selector.keys()) {
//                                k.channel().close();
//                            }
//                            selector.selectNow();
//                            Set<SelectionKey> sk = selector.selectedKeys();
//                            for (SelectionKey k: sk){
//                                k.cancel();
//                                k.channel().close();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldWait(){
        long now = System.currentTimeMillis();
        if (now-last>3000) return true;
        return false;
    }

    private void wakeUp(){
        last = System.currentTimeMillis();
    }

    public int getAwait() {
        return selector.keys().size();
    }
}
