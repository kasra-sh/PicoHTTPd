package ir.kasra_sh.picohttpd.http.response.cookie;

public class Cookie {
    private StringBuilder ck;
    private Cookie(){
        ck = new StringBuilder(2048);
    }

    public static Cookie builder() {
        return new Cookie();
    }

    public static Cookie make(String key, String value, String ... options) {
        Cookie c = new Cookie();
        c.set(key,value);
        for (int i = 0; i < options.length; i++) {
            c.opt(options[i]);
        }
        return c;
    }

    public Cookie set(String key, String value) {
        if (ck.length()>0) ck.append("; ");
        ck.append(key).append("=").append(value);
        return this;
    }

    public Cookie opt(String op) {
        if (ck.length()>0) {
            ck.append("; ");
        }
        ck.append(op);
        return this;
    }

    public String build(){
        return ck.toString();
    }
}
