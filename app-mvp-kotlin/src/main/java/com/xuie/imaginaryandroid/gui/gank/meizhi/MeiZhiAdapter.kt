package com.xuie.imaginaryandroid.gui.gank.meizhi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.BaseBean
import com.xuie.imaginaryandroid.util.GlideUtils

/**
 * Created by xuie on 17-7-5.
 */

class MeiZhiAdapter(meiZhis: List<BaseBean>?) : BaseQuickAdapter<BaseBean, BaseViewHolder>(R.layout.item_fragment_meizhi, meiZhis) {

    override fun convert(helper: BaseViewHolder, item: BaseBean) {
        helper.setText(R.id.date, item.desc)
        GlideUtils.loadImageMeizhi(mContext, item.url, helper.getView(R.id.meizhi))
    }

    companion object {
        private val TAG = "MeiZhiAdapter"
    }
}
