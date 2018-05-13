package me.zsr.rss.common;

import android.support.annotation.Nullable;

import org.mozilla.universalchardet.UniversalDetector;

public class StringUtil {

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if (str1 == null || str2 == null) {
            return false;
        }

        return str1.equals(str2);
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0; // string.isEmpty() in Java 6
    }

    public static String guessEncoding(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        String DEFAULT_ENCODING = "UTF-8";
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
        return encoding;
    }
}
