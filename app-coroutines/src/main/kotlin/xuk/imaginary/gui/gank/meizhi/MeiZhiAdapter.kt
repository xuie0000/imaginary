package xuk.imaginary.gui.gank.meizhi

import android.widget.ImageView
import coil.api.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.imaginary.R
import xuk.imaginary.data.GkIo

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class MeiZhiAdapter internal constructor(meiZhi: List<GkIo.BaseBean>) :
    BaseQuickAdapter<GkIo.BaseBean, BaseViewHolder>(R.layout.item_fragment_meizhi, meiZhi) {

  override fun convert(helper: BaseViewHolder, item: GkIo.BaseBean) {
    helper.setText(R.id.date, item.desc)
    helper.getView<ImageView>(R.id.meizhi).load(item.url)
  }
}
