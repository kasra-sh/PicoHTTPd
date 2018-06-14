package ir.kasra_sh.picohttpd.server;

public class Stat {
    private int awaitRead;
    private int awaitWrite;
    private float rps;

    public Stat(int awaitRead, int awaitWrite, float rps) {
        this.awaitRead = awaitRead;
        this.awaitWrite = awaitWrite;
        this.rps = rps;
    }

    public void setRps(float rps) {
        this.rps = rps;
    }

    public float getRps() {
        return rps;
    }

    public int getAwaitRead() {
        return awaitRead;
    }

    public int getAwaitWrite() {
        return awaitWrite;
    }
}
