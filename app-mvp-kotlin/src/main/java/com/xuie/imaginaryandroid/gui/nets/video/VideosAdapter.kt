package com.xuie.imaginaryandroid.gui.nets.video

import android.text.TextUtils
import android.widget.ImageView

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.VideoBean
import com.xuie.imaginaryandroid.glide.GlideApp

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 * Created by xuie on 17-8-22.
 */

class VideosAdapter(videoBeen: List<VideoBean>) : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_video_list, videoBeen) {

    @Override
    protected fun convert(helper: BaseViewHolder, item: VideoBean) {
        helper.setText(R.id.tv_from, item.getTopicName())
        helper.setText(R.id.tv_play_time,
                String.format(mContext.getResources().getString(R.string.video_play_times),
                        String.valueOf(item.getPlayCount())
                )
        )
        val jcVideoPlayerStandard = helper.getView(R.id.videoplayer)
        jcVideoPlayerStandard.setUp(
                item.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                if (TextUtils.isEmpty(item.getDescription())) item.getTitle() + "" else item.getDescription())
        GlideApp.with(mContext).load(item.getCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.mipmap.ic_empty_picture)
                .into(jcVideoPlayerStandard.thumbImageView)
        GlideApp.with(mContext).load(item.getTopicImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_empty_picture)
                .centerCrop()
                /*.transform(new GlideRoundTransformUtil(context))*/
                .into(helper.getView(R.id.iv_logo) as ImageView)
    }

    companion object {
        private val TAG = "VideosAdapter"
    }
}
