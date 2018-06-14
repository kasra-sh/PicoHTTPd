package ir.kasra_sh.picohttpd.server.interfaces;

import java.io.InputStream;
import java.nio.channels.SocketChannel;

public interface WriteListener {
    void onWrite(SocketChannel s, byte[] b);
    void onWriteStream(SocketChannel s, byte[] data, InputStream is, int len);
}
