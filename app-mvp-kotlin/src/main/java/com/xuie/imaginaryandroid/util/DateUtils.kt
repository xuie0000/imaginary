package com.xuie.imaginaryandroid.util

import java.util.regex.Pattern

/**
 * Created by xuie on 17-8-15.
 */

object DateUtils {
    private val MATCH_REGEX = "(\\d+)-(\\d+)-(\\d+)T(\\S+)Z"

    fun getDate(s: String): String {
        val m = Pattern.compile(MATCH_REGEX).matcher(s)
        return if (!m.matches() || m.groupCount() < 4) {
            ""
        } else m.group(1) + "/" + m.group(2) + "/" + m.group(3)
    }
}
