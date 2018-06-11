package ir.kasra_sh.nanoserver.server.tasks.models;

import ir.kasra_sh.nanoserver.server.nio.RequestParser;

import java.nio.channels.SocketChannel;

public class ReadPair {
    private SocketChannel socket;
    private RequestParser parser;

    public ReadPair(SocketChannel socket, RequestParser parser) {
        this.socket = socket;
        this.parser = parser;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    public RequestParser getParser() {
        return parser;
    }

    public void setParser(RequestParser parser) {
        this.parser = parser;
    }
}
