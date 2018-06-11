package ir.kasra_sh.nanoserver.server.interfaces;

import ir.kasra_sh.nanoserver.http.request.Request;
import ir.kasra_sh.nanoserver.server.nio.ResponseWriter;

public interface HTTPHandler {
    void handleRequest(Request r, ResponseWriter w);
}
