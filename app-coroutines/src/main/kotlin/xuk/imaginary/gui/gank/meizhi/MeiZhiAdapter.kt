package xuk.imaginary.gui.gank.meizhi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.imaginary.R
import xuk.imaginary.data.GankIo
import xuk.imaginary.util.GlideUtils

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class MeiZhiAdapter internal constructor(meiZhi: List<GankIo.BaseBean>) :
    BaseQuickAdapter<GankIo.BaseBean, BaseViewHolder>(R.layout.item_fragment_meizhi, meiZhi) {

  override fun convert(helper: BaseViewHolder, item: GankIo.BaseBean) {
    helper.setText(R.id.date, item.desc)
    GlideUtils.loadImageMeizhi(mContext, item.url, helper.getView(R.id.meizhi))
  }
}
