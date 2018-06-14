package ir.kasra_sh.picohttpd.utils;

import java.nio.ByteBuffer;

public class BufferPool {

    public static ByteBuffer pullOut() {
        return ByteBuffer.allocate(8192);
    }

    public static void pushBack(ByteBuffer buffer) {
        buffer.clear();
    }
}
