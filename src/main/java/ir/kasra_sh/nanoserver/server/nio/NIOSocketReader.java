package ir.kasra_sh.nanoserver.server.nio;

import ir.kasra_sh.nanoserver.utils.BufferPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOSocketReader {
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;
    private boolean finished = false;

    private boolean r = false;
    private boolean gotHead = false;
    private int cur = 0;
    private int len = 0;
    StringBuilder line = new StringBuilder(8192);

    public NIOSocketReader(SocketChannel channel) {
        socketChannel = channel;
        byteBuffer = BufferPool.pullOut();
        byteBuffer.clear();
        byteBuffer.flip();
    }

    public void finish() {
        if (byteBuffer != null)
            BufferPool.pushBack(byteBuffer);
    }

    private void fillBuffer() throws IOException {
        if (byteBuffer.hasRemaining()) return;
        byteBuffer.clear();
        int i = socketChannel.read(byteBuffer);
        byteBuffer.flip();
    }

    public String readLine() throws IOException {
        fillBuffer();
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            line.append((char) b);
            if (b == '\n' && r) {
                r = false;
                line.setLength(line.length() - 2);
                String ret = line.toString();
                line.setLength(0);
                return ret;
            } else if (b == '\r') {
                r = true;
            } else
                r = false;

            if (line.length() > 8191) {
                System.out.println("HEADER:\n" + line.toString());
                throw new IOException("Headerline too Long!");
            }
            fillBuffer();
        }
        return null;
    }


    private int readCur = 0;
    public boolean readRemaining(byte[] b) throws IOException {
        if (b.length <= 0) {
            System.out.println("NOLEN");
            throw new IOException("first specify readLen");
        }
        len = b.length;

        fillBuffer();
        while (byteBuffer.hasRemaining()) {
//            int bef = byteBuffer.remaining();
            b[readCur++] = byteBuffer.get();
            if (readCur == len) return true;
            fillBuffer();
        }

        return false;
    }

    public boolean isFinished() {
        return finished;
    }
}
