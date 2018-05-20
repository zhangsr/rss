package me.zsr.rss.common;

import me.zsr.rss.BuildConfig;

public class ExceptionHandler {

    public static void silentHandle(Exception e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }
}
