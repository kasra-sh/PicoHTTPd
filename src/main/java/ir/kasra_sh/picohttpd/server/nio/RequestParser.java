package ir.kasra_sh.picohttpd.server.nio;

import ir.kasra_sh.picohttpd.http.request.Request;
import ir.kasra_sh.picohttpd.http.request.HTTPMethod;

import java.io.IOException;

public class RequestParser {
    private NIOSocketReader reader;
    private Request req = new Request();
    private boolean rheader = false;
    private boolean hasBody = false;
    private boolean rbody = false;
    private int blen = 0;

    public void finish(){
        reader.finish();
    }

    public RequestParser(NIOSocketReader reader) {
        this.reader = reader;
    }

    public Request req() {
        return req;
    }

    // Header //

    String l = null;

    public boolean readHeader() throws IOException {
        if (rheader) return true;
        do {
            l = reader.readLine();
            if (l == null) return false;
            if (l.equals("")) {
                finishHeader();
                return true;
            }
            parseLine(l);
        } while (true);
    }

    private void finishHeader() {
        rheader = true;
        blen = req.contentLength();
        if (blen > 0) {
            hasBody = true;
        }
    }

    // Body //

    public boolean readBody() throws IOException {
        if (!hasBody) throw new IOException("Why???");
        req.setBody(new byte[blen]);
        if (reader.readRemaining(req.getBody())) {
            return true;
        }
        return false;
    }

    ///////////////////////////////////
    // Parsing

    private boolean head = false;

    private void parseLine(String line) throws HTTPParseException {
        if (!head) {
            parseHead(line);
            head = true;
            return;
        }
        String[] hd = line.split(": ", 2);
        if (hd.length != 2) {
            throw new HTTPParseException("Bad header line!");
        }
        req.putHeader(hd[0], hd[1]);
    }

    private void parseHead(String line) throws HTTPParseException {
        String[] hdp = line.split(" ");
        if (hdp.length != 3) throw new HTTPParseException("Bad header !");
        req.method(HTTPMethod.fromStr(hdp[0]));
        parseURL(hdp[1]);
        req.setVersion(hdp[2]);
    }

    private void parseURL(String url) throws HTTPParseException {
        if (!url.startsWith("/")) throw new HTTPParseException("Bad URL format!");
        String[] ua;
        try {
            ua = url.split("\\?", 2);
        } catch (Exception e) {
            throw new HTTPParseException(e);
        }
        if (ua.length == 0) throw new HTTPParseException("Empty URL!");
        // URL
        req.setUrl(ua[0]);
        // Args
        if (ua.length > 1) {
            String[] args = ua[1].split("&");
            for (int i = 0; i < args.length; i++) {
                String[] kv = args[i].split("=", 2);
                if (kv.length != 2)
                    throw new HTTPParseException("Bad arg(key=value) format!");

                if (kv[0].isEmpty() || kv[1].isEmpty())
                    throw new HTTPParseException("Bad arg(key=value) format!");

                req.putArg(kv[0], kv[1]);
            }
        }
    }

    public boolean hasBody() {
        return hasBody;
    }
}
