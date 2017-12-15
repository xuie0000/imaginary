package com.xuie.imaginaryandroid.util;

import android.support.annotation.Nullable;

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

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

}
