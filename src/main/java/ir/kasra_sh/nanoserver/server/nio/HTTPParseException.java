package ir.kasra_sh.nanoserver.server.nio;

import java.io.IOException;

public class HTTPParseException extends IOException {
    public HTTPParseException() {
        super();
    }

    public HTTPParseException(String s) {
        super(s);
    }

    public HTTPParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HTTPParseException(Throwable throwable) {
        super(throwable);
    }
}
