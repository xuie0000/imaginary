package com.xuie.imaginary.util

import java.util.regex.Pattern

/**
 * @author Jie Xu
 * @date 17-8-15
 */
object DateUtils {
  private const val MATCH_REGEX = "(\\d+)-(\\d+)-(\\d+)T(\\S+)Z"

  fun getDate(s: String?): String {
    if (s.isNullOrEmpty()) {
      return ""
    }
    val m = Pattern.compile(MATCH_REGEX).matcher(s)
    return if (m == null || !m.matches() || m.groupCount() < 4) {
      ""
    } else m.group(1) + "/" + m.group(2) + "/" + m.group(3)
  }
}
