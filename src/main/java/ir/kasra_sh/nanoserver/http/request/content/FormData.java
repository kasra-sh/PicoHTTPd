package ir.kasra_sh.nanoserver.http.request.content;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class FormData {
    private byte[] data;
    private ArrayList<Map.Entry<String, String>> headers = new ArrayList<>(3);

    public FormData(byte[] data, ArrayList<Map.Entry<String, String>> headers) {
        this.data = data;
        this.headers = headers;
    }

    public void putHeader(String k, String v) {
        headers.add(new AbstractMap.SimpleEntry<String, String>(k.trim(),v));
    }

    public String getHeader(String k){
        for (Map.Entry<String,String> e: headers) {
            if (e.getKey().equalsIgnoreCase(k)) {
                return e.getValue();
            }
        }
        return null;
    }

    public String name() {
        String[] v = getHeader("Content-Disposition").split("; ");
        for (int i = 0; i < v.length; i++) {
            String[] kv = v[i].split("=");
            if (kv[0].equalsIgnoreCase("name")) {
                return kv[1].substring(1,kv[1].length()-1);
            }
        }
        return "";
    }

    public String contentType() {
        return getHeader("Content-Type");
    }

    public String fileName() {
        String[] v = getHeader("Content-Disposition").split("; ");
        for (int i = 0; i < v.length; i++) {
            String[] kv = v[i].split("=");
            if (kv[0].equalsIgnoreCase("filename")) {
                return kv[1].substring(1,kv[1].length()-1);
            }
        }
        return null;
    }

    public FormData() {
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ArrayList<Map.Entry<String, String>> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<Map.Entry<String, String>> headers) {
        this.headers = headers;
    }
}
