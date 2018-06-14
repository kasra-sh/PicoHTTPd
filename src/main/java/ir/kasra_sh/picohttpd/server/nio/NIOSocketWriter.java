package ir.kasra_sh.picohttpd.server.nio;

import ir.kasra_sh.picohttpd.utils.BufferPool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOSocketWriter {
    private SocketChannel socket;
    private ByteBuffer bb;
    private boolean stream = false;
    private StreamBuffer sb;

    public NIOSocketWriter(SocketChannel socketChannel, byte[] bytes) {
        socket = socketChannel;
        bb = ByteBuffer.wrap(bytes);
//        bb.flip();
    }

    public NIOSocketWriter(SocketChannel socketChannel, byte[] bytes, InputStream stream, int len) throws IOException {
        socket = socketChannel;
        this.stream = true;
        bb = ByteBuffer.wrap(bytes);
        sb = new StreamBuffer(stream, len);
    }

    public boolean advance() {
        if (bb==null) return advanceStream();
        if (!bb.hasRemaining()) {
            bb.clear();
            if (bb!=null) {
                BufferPool.pushBack(bb);
                bb = null;
            }
            return advanceStream();
        }
        try {
            socket.write(bb);
            if (bb.hasRemaining()) {
                return true;
            } else {
                bb.clear();
                if (bb!=null) {
                    BufferPool.pushBack(bb);
                    bb = null;
                }
                return advanceStream();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            if (bb!=null) {
                bb.clear();
                BufferPool.pushBack(bb);
                bb = null;
            }
            return advanceStream();
        }
    }

    private boolean advanceStream() {
        if (!stream) {
//            System.out.println("Not Stream");
            return false;
        }
        try {
//            System.out.println("Adv Stream");
            ByteBuffer bb = sb.getReadableBuffer();
//            System.out.println("bbrem : "+bb.remaining());
            socket.write(bb);
//            System.out.println(sb.isFinished());
            return !sb.isFinished();
        } catch (Exception e){
//            e.printStackTrace();
        }
        return false;
    }
}

