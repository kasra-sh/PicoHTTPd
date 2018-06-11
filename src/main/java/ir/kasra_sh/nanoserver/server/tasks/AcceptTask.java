package ir.kasra_sh.nanoserver.server.tasks;

import ir.kasra_sh.nanoserver.server.interfaces.AcceptListener;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptTask implements Runnable {

    private AcceptListener listener;
    private ServerSocketChannel serverSocketChannel;

    public AcceptTask(AcceptListener listener, ServerSocketChannel serverSocketChannel) throws IOException {
        this.listener = listener;
        this.serverSocketChannel = serverSocketChannel;
        this.serverSocketChannel.configureBlocking(true);

    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel!=null) listener.onAccept(socketChannel);
            } catch (IOException e) {
//                listener.onAccept(null);
            }
        }
    }
}
