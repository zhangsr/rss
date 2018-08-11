package me.zsr.common;

public class NumberUtil {

    public static boolean equals(Long long1, Long long2) {
        if (long1 == null && long2 == null) {
            return true;
        }

        if (long1 == null || long2 == null) {
            return false;
        }

        return long1.equals(long2);
    }

    public static Long[] toLongArray(long[] longArray) {
        if (longArray == null) {
            return null;
        }
        Long result [] = new Long[longArray.length];
        for (int i = 0; i < longArray.length; i++) {
            result[i] = longArray[i];
        }
        return result;
    }
}
