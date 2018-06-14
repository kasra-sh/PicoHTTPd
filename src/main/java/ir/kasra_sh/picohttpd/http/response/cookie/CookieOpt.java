package ir.kasra_sh.picohttpd.http.response.cookie;

import java.util.Date;

public class CookieOpt {
    public static String expire(Date date){
        return "Expires="+date.toString();
    }

    public static String expireAfter(int y, int m, int d, int hour, int min, int sec) {
        long mil = System.currentTimeMillis()
                +(sec*1000)
                +(min*60000)
                +(hour*60000*60)
                +(d*60000*60*24)
                +(m*60000*60*24*30)
                +(y*60000*60*24*30*365);
        return "Expires="+new Date(mil);
    }

    public static String expireDay(int days) {
        long mil = System.currentTimeMillis()
                +(days*60000*60*24);
        return "Expires="+new Date(mil);
    }

    public static String expireSec(int sec) {
        long mil = System.currentTimeMillis()
                +(sec*1000);
        return "Expires="+new Date(mil);
    }

    public static String expireTime(int hour, int min, int sec) {
        long mil = System.currentTimeMillis()
                +(sec*1000)
                +(min*60000)
                +(hour*60000*60);
        return "Expires="+new Date(mil);
    }

    public static String secure(){
        return "Secure";
    }

    public static String httpOnly(){
        return "HttpOnly";
    }
}
