package com.xuie.imaginaryandroid.gui.nets.news

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.util.GlideUtils

/**
 * Created by xuie on 17-7-5.
 */

class NewsListAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(data: List<NetsSummary>?) : BaseMultiItemQuickAdapter<NetsSummary, BaseViewHolder>(data) {

    init {
        addItemType(NetsSummary.IMG_ONE, R.layout.item_fragment_news)
        addItemType(NetsSummary.IMG_MULTI, R.layout.item_fragment_news_multi)
    }

    override fun convert(helper: BaseViewHolder, item: NetsSummary) {
        when (helper.itemViewType) {
            NetsSummary.IMG_ONE -> {
                helper.setText(R.id.ltitle, item.title)
                        .setText(R.id.source, item.source)
                        .setText(R.id.digest, item.digest)
                GlideUtils.loadImage(mContext, item.imgsrc, helper.getView(R.id.img_src))
            }
            NetsSummary.IMG_MULTI -> {
                helper.setText(R.id.ltitle, item.title)
                        .setText(R.id.source, item.source)
                        .setText(R.id.digest, item.digest)
                val rv = helper.getView<RecyclerView>(R.id.img_src)

                rv.layoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL,
                        false)
                val sa = SimpleAdapter(item.imgextra!!)
                rv.adapter = sa
            }
        }
    }

    private inner class SimpleAdapter(data: List<NetsSummary.ImgextraBean>) :
            BaseQuickAdapter<NetsSummary.ImgextraBean, BaseViewHolder>(R.layout.item_fragment_news_child, data) {

        override fun convert(helper: BaseViewHolder, item: NetsSummary.ImgextraBean) {
            GlideUtils.loadImage(mContext, item.imgsrc, helper.getView(R.id.iv_image))
        }
    }

    companion object {
        private val TAG = "NewsListAdapter"
    }
}
