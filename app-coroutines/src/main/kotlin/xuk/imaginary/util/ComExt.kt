package xuk.imaginary.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import xuk.imaginary.R
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

fun ImageView.loadImage(url: String) {
  Glide.with(context)
      .load(url)
      .apply(RequestOptions().fitCenter()
          .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      )
      .transition(withCrossFade())
      .into(this)
}

fun List<String>.isNotEmptyOrBlank(): Boolean {
  return this.isNotEmpty() && !this[0].isBlank()
}

