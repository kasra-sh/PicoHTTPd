package ir.kasra_sh.picohttpd.server.tasks.models;

import java.io.InputStream;
import java.nio.channels.SocketChannel;

public class WritePair {
    private SocketChannel socket;
    private byte[] bytes;

    private boolean stream =false;
    private InputStream is;
    private int len;

    public WritePair() {
    }

    public WritePair(SocketChannel socket, byte[] bytes) {
        this.socket = socket;
        this.bytes = bytes;
    }

//    public WritePair(SocketChannel socket, InputStream inputStream, int length){
//        setStream(true);
//        setInputStream(inputStream);
//        setLen(length);
//    }

    public WritePair(SocketChannel socket, byte[] data, InputStream inputStream, int length){
        setSocket(socket);
        setBytes(data);
        setStream(true);
        setInputStream(inputStream);
        setLen(length);
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public InputStream getInputStream() {
        return is;
    }

    public void setInputStream(InputStream is) {
        this.is = is;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
