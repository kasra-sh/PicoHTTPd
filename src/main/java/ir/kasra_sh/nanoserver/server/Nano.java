package ir.kasra_sh.nanoserver.server;

import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.http.response.Response;
import ir.kasra_sh.nanoserver.server.interfaces.HTTPHandler;
import ir.kasra_sh.nanoserver.server.nio.ResponseWriter;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Nano {
    private NanoServer nanoServer;
    private Thread mainThread;
    private Executor ex;
    private HTTPHandler httpHandler = new HTTPHandler() {
        @Override
        public void handleRequest(Request r, ResponseWriter w) {
            w.write(Response.make(200).bodyText("hi\n".getBytes()));
        }
    };

    public static Nano getDefault() {
        Nano nano = new Nano();
        nano.ex = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
        return nano;
    }

    public static Nano get(){
        return new Nano();
    }

    public Nano setHandler(HTTPHandler handler) {
        this.httpHandler = handler;
        return this;
    }

    public Nano setExecutor(Executor executor){
        this.ex = executor;
        return this;
    }

    public void start(int port) throws IOException {
        nanoServer = new NanoServer(port);
        nanoServer.setExecutor(ex);
        nanoServer.setHandler(httpHandler);
        mainThread = new Thread(nanoServer);
        mainThread.setName("NanoMainThread");
        mainThread.start();
    }

    public Stat getStat(){
        return nanoServer.getStat();
    }
}
