package ir.kasra_sh.nanoserver.server.interfaces;

import java.nio.channels.SocketChannel;

public interface FailListener {
    void onFail(SocketChannel s);
}
