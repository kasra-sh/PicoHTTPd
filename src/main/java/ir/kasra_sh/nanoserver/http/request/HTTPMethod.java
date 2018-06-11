package ir.kasra_sh.nanoserver.http.request;

public enum HTTPMethod {
    NONE,
    HEAD,
    GET,
    POST,
    OPTIONS,
    DELETE,
    CONNECT,
    PUT;
    public static String toStr(HTTPMethod method){
        switch (method){
            case HEAD:return "HEAD";
            case GET:return "GET";
            case POST:return "POST";
            case OPTIONS:return "OPTIONS";
            case DELETE:return "DELETE";
            case PUT:return "PUT";
                default:return "";
        }
    }

    public static HTTPMethod fromStr(String method){
        String m = method.toUpperCase();
        switch (m) {
            case "HEAD": return HEAD;
            case "GET":return GET;
            case "POST": return POST;
            case "OPTIONS": return OPTIONS;
            case "DELETE": return DELETE;
            case "CONNECT": return CONNECT;
            case "PUT": return PUT;
            default:
                return NONE;
        }
    }
}
