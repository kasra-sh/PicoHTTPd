package ir.kasra_sh.nanoserver.server.tasks;


import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public class DeadWatcher {
    private ConcurrentHashMap<SocketChannel, Long> socks = new ConcurrentHashMap<>();

    public void add(SocketChannel s) {
        socks.put(s, System.currentTimeMillis());
    }

    public boolean shouldRemove(SocketChannel s) {
        Long st = socks.get(s);
        if (st != null)
            return System.currentTimeMillis() - socks.get(s) > 5000;
        else
            return false;
    }

    public void remove(SocketChannel s) {
        socks.remove(s);
    }

}
