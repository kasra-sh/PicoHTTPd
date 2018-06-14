package ir.kasra_sh.picohttpd.server.interfaces;

import java.nio.channels.SocketChannel;

public interface WriteFinishListener {
    void onWriteFinished(SocketChannel socketChannel);
}
