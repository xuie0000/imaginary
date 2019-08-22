package xuk.imaginary.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xuk.imaginary.gui.gank.show.MeiZhi

/**
 * @author Jie Xu
 * @date 2018-5-29
 */
abstract class BaseViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

  /**
   * 绑定
   *
   * @param mz GK IO BEAN
   */
  abstract fun bind(mz: MeiZhi)
}
