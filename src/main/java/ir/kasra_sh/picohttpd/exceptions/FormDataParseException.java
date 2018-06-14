package ir.kasra_sh.picohttpd.exceptions;

import java.io.IOException;

public class FormDataParseException extends IOException {
    public FormDataParseException() {
        super();
    }

    public FormDataParseException(String s) {
        super(s);
    }

    public FormDataParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FormDataParseException(Throwable throwable) {
        super(throwable);
    }
}
