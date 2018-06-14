package ir.kasra_sh.picohttpd.server.interfaces;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.server.nio.ResponseWriter;

public interface HTTPHandler {
    void handleRequest(Request r, ResponseWriter w);
}
