package ir.kasra_sh.picohttpd.http.request;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Request {
    private HTTPMethod method = HTTPMethod.NONE;
    private String versionStr="HTTP/1.1";
    private String url="/";
    private ConcurrentHashMap<String, String> args = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> headers = new ConcurrentHashMap<>();
    private byte[] body = null;
    private SocketAddress address;

    public Request() {
    }

    // HTTP Method

    public HTTPMethod method() {
        return method;
    }

    public Request method(HTTPMethod m) {
        this.method = m;
        return this;
    }

    // Version

    public Request setVersion(String ver){
        versionStr = ver;
        return this;
    }

    public String getVersion(){
        return versionStr;
    }

    // URL Arguments (?key1=value1&key2=value2)

    public Request putArg(String k, String v){
        args.put(k,v);
        return this;
    }

    public String getArg(String k) {
        return args.get(k);
    }

    public boolean hasArg(String k) {
        return args.containsKey(k);
    }

    // HTTP Headers

    public Request putHeader(String k, String v){
        headers.put(k.toLowerCase(),v);
        return this;
    }

    Request putHeader(String k, String ... vs){
        StringBuilder v = new StringBuilder(vs.length*1024);
        for (int i = 0; i < vs.length; i++) {
            v.append(vs[i]);
            if (i<(vs.length-1)) {
                v.append("; ");
            }
        }
        return putHeader(k,v.toString());
    }

    public String getHeader(String k) {
        return headers.get(k.toLowerCase());
    }

    public boolean hasHeader(String k) {
        return headers.containsKey(k.toLowerCase());
    }

    public boolean hasBody() {
        return hasHeader("content-length");
    }

    public int contentLength(){
        if (hasBody()) {
            return Integer.valueOf(getHeader("content-length"));
        } else {
            return 0;
        }
    }

    public Request contentLength(int len) {
        putHeader("content-length", String.valueOf(len));
        return this;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isGet(){
        return method == HTTPMethod.GET;
    }

    public boolean isPost(){
        return method == HTTPMethod.POST;
    }

    public boolean isHead(){
        return method == HTTPMethod.HEAD;
    }

    public boolean isDelete(){
        return method == HTTPMethod.DELETE;
    }

    public boolean isConnect(){
        return method == HTTPMethod.CONNECT;
    }

    public boolean isNone() {
        return method == HTTPMethod.NONE;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public ConcurrentHashMap<String, String> getHeaders() {
        return headers;
    }

    public ConcurrentHashMap<String, String> getArgs() {
        return args;
    }

}
