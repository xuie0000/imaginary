package com.xuie.imaginaryandroid.util;

/**
 * Created by xuie on 17-7-5.
 */

public class Utils {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
