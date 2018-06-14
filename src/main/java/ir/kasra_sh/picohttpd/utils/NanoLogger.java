package ir.kasra_sh.picohttpd.utils;

import java.util.Calendar;

public class NanoLogger {

    private static boolean logThr = true;

    private static Calendar calendar = Calendar.getInstance();

    private static OnLogListener logListener = new OnLogListener() {

        @Override
        public void onLogError(String tag, String text) {
            System.out.println(getTime() + " - (E) - " + tag + ": " + text);
        }

        @Override
        public void onLogInfo(String tag, String text) {
            System.out.println(getTime() + " - (I) - " + tag + ": " + text);
        }

        @Override
        public void onLogWarning(String tag, String text) {
            System.out.println(getTime() + " - (W) - " + tag + ": " + text);
        }

        @Override
        public void onLogDebug(String tag, String text) {
            System.out.println(getTime() + " - (D) - " + tag + ": " + text);
        }

        @Override
        public void onLogException(String tag, String text, Throwable throwable) {
            System.out.println(getTime() + " - (T) - " + tag + ": " + text);
        }
    };

    private static class LogFlags {
        public static boolean logError = true;
        public static boolean logInfo = true;
        public static boolean logWarning = true;
        public static boolean logDebug = true;
        public static boolean logException = true;

        public static void setFlags(boolean info,
                                    boolean warning,
                                    boolean error,
                                    boolean exception,
                                    boolean debug)
        {
            logError = error;
            logDebug = debug;
            logInfo = info;
            logWarning = warning;
            logException = exception;
        }
    }

    public static synchronized void setLogExceptions(boolean logExceptions) {
        logThr = logExceptions;
    }

    public static synchronized void setOnLogListener(OnLogListener listener) {
        logListener = listener;
    }

    /**
     * Log Error
     *
     * @param tag
     * @param text
     */
    public static void e(String tag, String text) {
        if (LogFlags.logError)
            logListener.onLogError(tag, text);
    }

    /**
     * Log Info
     *
     * @param tag
     * @param text
     */
    public static void i(String tag, String text) {
        if (LogFlags.logInfo)
            logListener.onLogInfo(tag, text);
    }

    /**
     * Log Warning
     *
     * @param tag
     * @param text
     */
    public static void w(String tag, String text) {
        if (LogFlags.logWarning)
            logListener.onLogWarning(tag, text);
    }

    /**
     * @param tag
     * @param text
     * @param throwable
     */
    public static void thr(String tag, String text, Throwable throwable) {
        if (LogFlags.logException)
            logListener.onLogException(tag, text, throwable);
    }

    /**
     * @param tag
     * @param throwable
     */
    public static void wtf(String tag, Throwable throwable) {
        thr(tag, "", throwable);
    }

    private static String getTime() {
        return calendar.getTime().toString();
    }

    public interface OnLogListener {
        void onLogError(String tag, String text);

        void onLogInfo(String tag, String text);

        void onLogWarning(String tag, String text);

        void onLogDebug(String tag, String text);

        void onLogException(String tag, String text, Throwable throwable);
    }
}
