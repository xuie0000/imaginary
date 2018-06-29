package com.xuie.imaginaryandroid.gui.nets.video

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.VideoBean
import com.xuie.imaginaryandroid.util.GlideUtils
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 * Created by xuie on 17-8-22.
 */

class VideosAdapter(videoBeen: List<VideoBean>?) : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_video_list, videoBeen) {

    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        helper.setText(R.id.tv_from, item.topicName)
        helper.setText(R.id.tv_play_time,
                String.format(mContext.resources.getString(R.string.video_play_times),
                        item.playCount.toString())
                )
        val jcVideoPlayerStandard = helper.getView<JCVideoPlayerStandard>(R.id.videoplayer)
        jcVideoPlayerStandard.setUp(
                item.mp4_url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                if (TextUtils.isEmpty(item.description)) item.title + "" else item.description)
        GlideUtils.loadImage(mContext, item.cover, jcVideoPlayerStandard.thumbImageView)
        GlideUtils.loadImage(mContext, item.topicImg, helper.getView(R.id.iv_logo))
    }

    companion object {
        private val TAG = "VideosAdapter"
    }
}
