package xuk.imaginary.util

import xuk.imaginary.data.GkIo
import xuk.imaginary.gui.gank.show.MeiZhi
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

fun GkIo.GkBean.convertToMei(): List<MeiZhi> {
  val ms: MutableList<MeiZhi> = ArrayList()
  for (gb in this.results) {
    val title = gb.key
    ms.add(MeiZhi(title, null, 0))
    for (bb in gb.value) {
      ms.add(MeiZhi(title, bb, 1))
    }
  }
  return ms
}

