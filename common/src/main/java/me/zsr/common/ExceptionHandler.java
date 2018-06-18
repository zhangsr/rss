package me.zsr.common;

public class ExceptionHandler {

    public static void silentHandle(Exception e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }
}
