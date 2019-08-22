package xuk.imaginary.util

import java.util.regex.Pattern

/**
 * @author Jie Xu
 * @date 17-8-15
 */
private const val MATCH_REGEX = "(\\d+)-(\\d+)-(\\d+)T(\\S+)Z"

fun String.gkSwitchDate(): String {
  val m = Pattern.compile(MATCH_REGEX).matcher(this)
  return if (!m.matches() || m.groupCount() < 4) {
    ""
  } else m.group(1) + "/" + m.group(2) + "/" + m.group(3)
}

