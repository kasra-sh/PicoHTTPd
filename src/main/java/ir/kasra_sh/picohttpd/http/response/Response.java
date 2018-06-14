package ir.kasra_sh.picohttpd.http.response;

import ir.kasra_sh.picohttpd.http.response.cookie.Cookie;
import ir.kasra_sh.picohttpd.utils.MimeTypes;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map.*;
import java.util.AbstractMap.SimpleEntry;

public class Response {
    private static Calendar cl;
    static {
        cl = Calendar.getInstance();
    }

    private int status;
    private byte[] body;
    private boolean streamBody = false;
    private int sBodyLen;
    private InputStream sBody;
    private ArrayList<Entry<String,String>> headers = new ArrayList<>();
    private ArrayList<Cookie> cookies = new ArrayList<>();

    private Response() {
        header("Server","Pico");
        header("Date",cl.getTime().toString());
    }

    public static Response make(int code) {
        Response r = new Response();
        r.status = code;
        return r;
    }

    public static Response makeText(int code, byte[] body){
        return make(code).bodyText(body);
    }

    public Response type(String mime) {
        setMime(mime);
        return this;
    }

    public Response body(byte[] body){
        this.body = body;
        header("Content-Length", String.valueOf(body.length));
        return this;
    }

    public Response body(byte[] body, String mime) {
        return body(body).type(mime);
    }

    public Response bodyJson(byte[] body) {
        return body(body, MimeTypes.Application.JSON);
    }

    public Response bodyHtml(byte[] body) {
        return body(body, MimeTypes.Text.HTML);
    }

    public Response bodyText(byte[] body) {
        return body(body, MimeTypes.Text.TXT);
    }

    public Response bodyBinary(byte[] body) {
        return body(body, MimeTypes.Application.BIN);
    }

    public Response header(String k, String v) {
        headers.add(new SimpleEntry<String, String>(k,v));
        return this;
    }

    public byte[] asByteArray() {
        StringBuilder sb = new StringBuilder(headers.size()*2048);
        // HEAD
        sb.append("HTTP/1.1 ").append(ResponseString.codeToString(status)).append("\r\n");
        for (Entry<String,String> hdr: headers) {
            sb.append(hdr.getKey()).append(": ").append(hdr.getValue()).append("\r\n");
        }

        for (Cookie c: cookies) {
            sb.append("Set-Cookie: ").append(c.build()).append("\r\n");
        }
        sb.append("\r\n");

        String bs = sb.toString();
        byte[] hd = bs.getBytes(StandardCharsets.UTF_8);
        if (body==null) body = new byte[0];
        byte[] all = new byte[hd.length+body.length];

        for (int i = 0; i < hd.length; i++) {
            all[i] = hd[i];
        }
        int l = hd.length;
        for (int i = l; i < all.length; i++) {
            all[i] = body[i-l];
        }

        return all;
    }

    public Response setCookie(String key, String value, String ... opts) {
        cookies.add(Cookie.make(key,value,opts));
        return this;
    }

    public int getStatus() {
        return status;
    }

    public boolean isStreamBody() {
        return streamBody;
    }

    public void setStreamBody(boolean streamBody) {
        this.streamBody = streamBody;
    }

    public InputStream getSBody() {
        return sBody;
    }

    public void setSBody(InputStream sBody) {
        this.sBody = sBody;
    }

    public String getMime() {
        for (Entry<String,String> e: headers) {
            if (e.getKey().equalsIgnoreCase("Content-Type")) {
                return e.getValue();
            }
        }
        return null;
    }

    public Response setMime(String mime) {
        if (getMime()==null)
            headers.add(new SimpleEntry<String, String>("Content-Type",mime));
        else {
            for (Entry<String, String> h : headers) {
                if (h.getKey().equalsIgnoreCase("Content-Type")) {
                    h.setValue(mime);
                    return this;
                }
            }
        }
        return this;
    }

    public ArrayList<Cookie> getCookies() {
        return cookies;
    }

    public ArrayList<Entry<String, String>> getHeaders() {
        return headers;
    }

    public int getSBodyLen() {
        return sBodyLen;
    }

    public void setSBodyLen(int sBodyLen) {
        this.sBodyLen = sBodyLen;
    }
}
