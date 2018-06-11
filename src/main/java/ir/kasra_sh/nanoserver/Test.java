package ir.kasra_sh.nanoserver;

import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.http.response.Response;
import ir.kasra_sh.nanoserver.server.Nano;
import ir.kasra_sh.nanoserver.server.Stat;
import ir.kasra_sh.nanoserver.server.interfaces.HTTPHandler;
import ir.kasra_sh.nanoserver.server.nio.ResponseWriter;
import java.io.IOException;

public class Test {
    public static void main(String... args) {
        new Test(args);
    }

    Test(String... args) {
        try {
            Nano n = Nano.getDefault();
            n.setHandler(new HTTPHandler() {
                @Override
                public void handleRequest(Request r, ResponseWriter w) {
                    w.write(Response.makeText(200,"Welcome to NanoServer!\n".getBytes()));
                }
            });
            n.start(8000);

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Stat s = n.getStat();
                System.out.print("ReadAwait(" + s.getAwaitRead()+")");
                System.out.print("\tWriteAwait(" + s.getAwaitWrite()+")");
                System.out.println("\tReq/Sec("+s.getRps()+")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
