package ir.kasra_sh.picohttpd.server.interfaces;

import java.nio.channels.SocketChannel;

public interface AcceptListener {
    void onAccept(SocketChannel socketChannel);
}
