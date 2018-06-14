package ir.kasra_sh.picohttpd.server.interfaces;

import ir.kasra_sh.picohttpd.server.nio.RequestParser;

import java.nio.channels.SocketChannel;

public interface ReadListener {
    void onReadFinished(SocketChannel socketChannel, RequestParser parser);
}
