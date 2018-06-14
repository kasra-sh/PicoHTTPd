package ir.kasra_sh.picohttpd.http.response;

public enum ResponseString {
    ;
    public static String CONTINUE = "100 Continue";
    public static String SWITCHING_PROTOCOLS = "101 Switching Protocols";
    public static String OK = "200 OK";
    public static String CREATED = "201 Created";
    public static String ACCEPTED = "202 Accepted";
    public static String NON_AUTHORITATIVE_INFO = "203 Non-Authoritative Information";
    public static String NO_CONTENT = "204 No Content";
    public static String RESET_CONTENT = "205 Reset Content";
    public static String PARTIAL_CONTENT = "206 Partial Content";
    public static String MULTIPLE_CHOICES = "300 Multiple Choices";
    public static String MOVED_PERMANENTLY = "301 Moved Permanently";
    public static String FOUND = "302 Found";
    public static String SEE_OTHER = "303 See Other";
    public static String NOT_MODIFIED = "304 Not Modified";
    public static String TEMPORARY_REDIRECT = "307 Temporary Redirect";
    public static String PERMANENT_REDIRECT = "308 Permanent Redirect";
    public static String BAD_REQUEST = "400 Bad Request";
    public static String UNAUTHORIZED = "401 Unauthorized";
    public static String FORBIDDEN = "403 Forbidden";
    public static String NOT_FOUND = "404 Not Found";
    public static String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
    public static String NOT_ACCEPTABLE = "406 Not Acceptable";
    public static String PROXY_AUTH_REQUIRED = "407 Proxy Authentication Required";
    public static String REQUEST_TIMEOUT = "408 Request Timeout";
    public static String CONFLICT = "409 Conflict";
    public static String GONE = "410 Gone";
    public static String LENGTH_REQUIRED = "411 Length Required";
    public static String PRECONDITION_FAILED = "412 Precondition Failed";
    public static String PAYLOAD_TOO_LARGE = "413 Payload Too Large";
    public static String URI_TOO_LONG = "414 URI Too Long";
    public static String UNSUPPORTED_MEDIA_TYPE = "415 Unsupported Media Type";
    public static String RANGE_NOT_SATISFIABLE = "416 Range Not Satisfiable";
    public static String EXPECTATION_FAILED = "417 Expectation Failed";
    public static String UPGRADE_REQUIRED = "426 Upgrade Required";
    public static String PRECONDITION_REQUIRED = "428 Precondition Required";
    public static String TOO_MANY_REQUESTS = "429 Too Many Requests";
    public static String REQUEST_HEADER_FIELDS_TOO_LAGRGE = "431 Request Request Fields Too Large";
    public static String UNAVAILABLE_FOR_LEGAL_REASONS = "451 Unavailable For Legal Reasons";
    public static String INTERNAL_SERVER_ERROR = "500 Internal server Error";
    public static String NOT_IMPLEMENTED = "501 Not Implemented";
    public static String BAD_GATEWAY = "502 Bad Gateway";
    public static String SERVICE_UNAVAILABLE = "503 Service Unavailable";
    public static String GATEWAY_TIMEOUT = "504 Gateway Timeout";
    public static String HTTP_VERSION_NOT_SUPPORTED = "505 HTTP Version Not Supported";
    public static String NETWORK_AUTH_REQUIRED = "511 Network Authentication Required";

    public static String codeToString(int rc){
        switch (rc){
            case ResponseCode.CONTINUE: return CONTINUE;
            case ResponseCode.SWITCHING_PROTOCOLS: return SWITCHING_PROTOCOLS;
            case ResponseCode.OK: return OK;
            case ResponseCode.CREATED: return CREATED;
            case ResponseCode.ACCEPTED: return ACCEPTED;
            case ResponseCode.NON_AUTHORITATIVE_INFORMATION: return NON_AUTHORITATIVE_INFO;
            case ResponseCode.NO_CONTENT: return NO_CONTENT;
            case ResponseCode.RESET_CONTENT: return RESET_CONTENT;
            case ResponseCode.PARTIAL_CONTENT: return PARTIAL_CONTENT;
            case ResponseCode.MULTIPLE_CHOICES: return MULTIPLE_CHOICES;
            case ResponseCode.MOVED_PERMANENTLY: return MOVED_PERMANENTLY;
            case ResponseCode.FOUND: return FOUND;
            case ResponseCode.SEE_OTHER: return SEE_OTHER;
            case ResponseCode.NOT_MODIFIED: return NOT_MODIFIED;
            case ResponseCode.TEMPORARY_REDIRECT: return TEMPORARY_REDIRECT;
            case ResponseCode.PERMANENT_REDIRECT: return PERMANENT_REDIRECT;
            case ResponseCode.BAD_REQUEST: return BAD_REQUEST;
            case ResponseCode.UNAUTHORIZED: return UNAUTHORIZED;
            case ResponseCode.FORBIDDEN: return FORBIDDEN;
            case ResponseCode.NOT_FOUND: return NOT_FOUND;
            case ResponseCode.METHOD_NOT_ALLOWED: return METHOD_NOT_ALLOWED;
            case ResponseCode.NOT_ACCEPTABLE: return NOT_ACCEPTABLE;
            case ResponseCode.PROXY_AUTH_REQUIRED: return PROXY_AUTH_REQUIRED;
            case ResponseCode.REQUEST_TIMEOUT: return REQUEST_TIMEOUT;
            case ResponseCode.CONFLICT: return CONFLICT;
            case ResponseCode.GONE: return GONE;
            case ResponseCode.LENGTH_REQUIRED: return LENGTH_REQUIRED;
            case ResponseCode.PRECONDITION_FAILED: return PRECONDITION_FAILED;
            case ResponseCode.TOO_MANY_REQUESTS: return TOO_MANY_REQUESTS;
            case ResponseCode.REQUEST_HEADER_FIELDS_TOO_LARGE: return REQUEST_HEADER_FIELDS_TOO_LAGRGE;
            case ResponseCode.UNAVAILABLE_FOR_LEGAL_REASONS: return UNAVAILABLE_FOR_LEGAL_REASONS;
            case ResponseCode.INTERNAL_SERVER_ERROR: return INTERNAL_SERVER_ERROR;
            case ResponseCode.NOT_IMPLEMENTED: return NOT_IMPLEMENTED;
            case ResponseCode.BAD_GATEWAY: return BAD_GATEWAY;
            case ResponseCode.SERVICE_UNAVALABLE: return SERVICE_UNAVAILABLE;
            case ResponseCode.GATEWAY_TIMEOUT: return GATEWAY_TIMEOUT;
            case ResponseCode.HTTP_VER_NOT_SUPPORTED: return HTTP_VERSION_NOT_SUPPORTED;
            case ResponseCode.NETWORK_AUTH_REQUIRED: return NETWORK_AUTH_REQUIRED;
            default: return BAD_REQUEST;

        }

    }
}
