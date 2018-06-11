package ir.kasra_sh.nanoserver.server.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class StreamBuffer {
    private ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
    private byte[] buf = new byte[8192];
    private InputStream inputStream;
    private int length;
    private int rLength;
    private boolean finished = false;

    public StreamBuffer(InputStream inputStream, int length) throws IOException {
        this.inputStream = inputStream;
        this.length = length;
        fill();
        byteBuffer.flip();
    }

    public ByteBuffer getReadableBuffer() throws IOException {
//        System.out.println("GRB");
//        System.out.println(byteBuffer.hasRemaining());
        if (byteBuffer.hasRemaining()) return byteBuffer;
        byteBuffer.clear();
        fill();
        byteBuffer.flip();
        return byteBuffer;
    }

    private void fill() throws IOException {
        int av = inputStream.available();
        if (av<=0) return;
        int l = inputStream.read(buf);
        if (l>=0) {
            rLength+=l;
            if (l>0) {
                byteBuffer.put(buf, 0, l);
            }
        }
        if (rLength==length) {
            finished = true;
            return;
        }

//        for (int i = 0; i < inputStream.available(); i++) {
//            if (byteBuffer.hasRemaining())
//            {
//                byteBuffer.put((byte) inputStream.read());
//                rLength++;
//                if (rLength==length) {
//                    System.out.println("FINISHED");
//                    finished = true;
//                    return rLength-length;
//                }
//            } else {
//                System.out.println("rem : "+(length - rLength));
//                return rLength-length;
//            }
//        }
    }

    public boolean isFinished() {
        return finished;
    }
}
