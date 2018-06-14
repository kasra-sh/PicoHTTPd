package ir.kasra_sh.picohttpd.server;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.response.Response;
import ir.kasra_sh.picohttpd.server.interfaces.HTTPHandler;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Pico {
    private PicoServer picoServer;
    private Thread mainThread;
    private Executor ex;
    private HTTPHandler httpHandler = new HTTPHandler() {
        @Override
        public void handleRequest(Request r, ResponseWriter w) {
            w.write(Response.make(200).bodyText("hi\n".getBytes()));
        }
    };

    public static Pico getDefault() {
        Pico pico = new Pico();
        pico.ex = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
        return pico;
    }

    public static Pico get(){
        return new Pico();
    }

    public Pico setHandler(HTTPHandler handler) {
        this.httpHandler = handler;
        return this;
    }

    public Pico setExecutor(Executor executor){
        this.ex = executor;
        return this;
    }

    public void start(int port) throws IOException {
        picoServer = new PicoServer(port);
        picoServer.setExecutor(ex);
        picoServer.setHandler(httpHandler);
        mainThread = new Thread(picoServer);
        mainThread.setName("NanoMainThread");
        mainThread.start();
    }

    public Stat getStat(){
        return picoServer.getStat();
    }
}
