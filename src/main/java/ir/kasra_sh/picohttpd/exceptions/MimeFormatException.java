package ir.kasra_sh.picohttpd.exceptions;

public class MimeFormatException extends Exception {
    public MimeFormatException() {
        super();
    }

    public MimeFormatException(String s) {
        super(s);
    }

    public MimeFormatException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MimeFormatException(Throwable throwable) {
        super(throwable);
    }

    protected MimeFormatException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
