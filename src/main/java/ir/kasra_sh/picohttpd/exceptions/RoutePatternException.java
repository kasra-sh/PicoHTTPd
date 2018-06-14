package ir.kasra_sh.picohttpd.exceptions;

public class RoutePatternException extends Exception {
    public RoutePatternException() {
        super();
    }

    public RoutePatternException(String s) {
        super(s);
    }

    public RoutePatternException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RoutePatternException(Throwable throwable) {
        super(throwable);
    }

    protected RoutePatternException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
