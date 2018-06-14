package ir.kasra_sh.picohttpd.interfaces;

import java.nio.channels.SocketChannel;

public interface ISocketWriter {
    void addSocket(SocketChannel socket, byte[] data);
}
