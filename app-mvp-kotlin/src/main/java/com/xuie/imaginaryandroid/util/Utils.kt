package com.xuie.imaginaryandroid.util

/**
 * Created by xuie on 17-7-5.
 */

object Utils {
    fun <T> checkNotNull(reference: T?): T {
        return reference ?: throw NullPointerException()
    }

    fun <T> checkNotNull(reference: T?, @Nullable errorMessage: Object): T {
        if (reference == null) {
            throw NullPointerException(String.valueOf(errorMessage))
        }
        return reference
    }

}
