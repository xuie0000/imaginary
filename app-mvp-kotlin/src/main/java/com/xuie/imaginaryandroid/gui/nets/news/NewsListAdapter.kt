package com.xuie.imaginaryandroid.gui.nets.news

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.glide.GlideApp

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
(data: List<NetsSummary>) : BaseMultiItemQuickAdapter<NetsSummary, BaseViewHolder>(data) {

    init {
        addItemType(NetsSummary.IMG_ONE, R.layout.item_fragment_news)
        addItemType(NetsSummary.IMG_MULTI, R.layout.item_fragment_news_multi)
    }

    @Override
    protected fun convert(helper: BaseViewHolder, item: NetsSummary) {
        when (helper.getItemViewType()) {
            NetsSummary.IMG_ONE -> {
                helper.setText(R.id.ltitle, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest())
                GlideApp.with(mContext)
                        .load(item.getImgsrc())
                        .override(300, 300)
                        .centerInside()
                        .into(helper.getView(R.id.img_src) as ImageView)
            }
            NetsSummary.IMG_MULTI -> {
                helper.setText(R.id.ltitle, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest())
                val rv = helper.getView(R.id.img_src)
                rv.setLayoutManager(GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false))
                val sa = SimpleAdapter(item.getImgextra())
                rv.setAdapter(sa)
            }
        }
    }

    private inner class SimpleAdapter(@Nullable data: List<NetsSummary.ImgextraBean>) : BaseQuickAdapter<NetsSummary.ImgextraBean, BaseViewHolder>(R.layout.item_fragment_news_child, data) {

        @Override
        protected fun convert(helper: BaseViewHolder, item: NetsSummary.ImgextraBean) {
            GlideApp.with(mContext)
                    .load(item.getImgsrc())
                    .apply(RequestOptions/*.circleCropTransform()*/
                            .placeholderOf(R.mipmap.ic_launcher_round)
                            .override(300, 300)
                            .centerInside()
                    )
                    .into(helper.getView(R.id.iv_image) as ImageView)
        }
    }

    companion object {
        private val TAG = "NewsListAdapter"
    }
}
