package ir.kasra_sh.nanoserver.server.tasks;

import ir.kasra_sh.nanoserver.server.interfaces.WriteFinishListener;
import ir.kasra_sh.nanoserver.server.nio.NIOSocketWriter;
import ir.kasra_sh.nanoserver.server.tasks.models.WritePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class WriteTask implements Runnable {
    private Selector selector;
    private WriteFinishListener listener;
    private ConcurrentLinkedQueue<WritePair> writes = new ConcurrentLinkedQueue<>();

    public WriteTask(WriteFinishListener listener) throws IOException {
        this.listener = listener;
        selector = Selector.open();
    }

    public void addWrite(SocketChannel s, byte[] data){
        writes.add(new WritePair(s,data));
    }

    public void addWrite(SocketChannel s, byte[] data, InputStream is, int len) {
        writes.add(new WritePair(s, data, is, len));
    }

    @Override
    public void run() {
        while (true){
            while (!writes.isEmpty()) {
                WritePair wp = writes.remove();
                try {
                    if (wp.isStream()) {
                        wp.getSocket().register(
                                selector,
                                SelectionKey.OP_WRITE,
                                new NIOSocketWriter(wp.getSocket(), wp.getBytes(), wp.getInputStream(), wp.getLen()));
                    } else {
                        wp.getSocket().register(
                                selector,
                                SelectionKey.OP_WRITE,
                                new NIOSocketWriter(wp.getSocket(), wp.getBytes())
                        );
                    }
                } catch (Exception e) { }
            }
            /////////////
            try {
                selector.select(1);
                Set<SelectionKey> keys = selector.selectedKeys();
                if (keys.size()>0) {
                    doWrites(keys);
                }
//                cycleWait();

            } catch (IOException e) { }
        }

    }

    private void doWrites(Set<SelectionKey> keys) {
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            try {
                doWriteKey(key);
            }catch (Exception e){
                key.cancel();
            }
            iterator.remove();
        }
    }

    private void doWriteKey(SelectionKey k){
        SocketChannel socket = (SocketChannel) k.channel();
        NIOSocketWriter writer = (NIOSocketWriter) k.attachment();
        wakeUp();
        if (!writer.advance()) {
            try {
                k.cancel();
            }catch (Exception e){
                try {
                    socket.close();
                } catch (IOException e1) { }
            }
            if (listener!=null) {
                listener.onWriteFinished(socket);
            }
        }
    }

    private long last = 0;

    private void cycleWait() {
        if (shouldWait()) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (shouldWait())
                        TimeUnit.NANOSECONDS.sleep(10);
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

    public int getAwait(){
        return selector.keys().size();
    }
}
