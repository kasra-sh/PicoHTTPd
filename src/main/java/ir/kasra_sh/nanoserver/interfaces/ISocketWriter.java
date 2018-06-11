package ir.kasra_sh.nanoserver.interfaces;

import java.nio.channels.SocketChannel;

public interface ISocketWriter {
    void addSocket(SocketChannel socket, byte[] data);
}
