package xuk.imaginary.gui.nets.video

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.imaginary.R
import xuk.imaginary.data.VideoBean
import xuk.imaginary.util.GlideUtils
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 * @author Jie Xu
 * @date 17-8-22
 */
class VideosAdapter internal constructor(videoBeen: List<VideoBean>) : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_video_list, videoBeen) {

  override fun convert(helper: BaseViewHolder, item: VideoBean) {
    helper.setText(R.id.tv_from, item.topicName)
    helper.setText(R.id.tv_play_time,
        String.format(mContext.resources.getString(R.string.video_play_times),
            item.playCount.toString()
        )
    )
    val jcVideoPlayerStandard = helper.getView<JCVideoPlayerStandard>(R.id.videoplayer)
    jcVideoPlayerStandard.setUp(
        item.mp4_url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
        if (TextUtils.isEmpty(item.description)) item.title!! + "" else item.description)
    GlideUtils.loadImageNetsVideo(mContext, item.cover!!, jcVideoPlayerStandard.thumbImageView)
    GlideUtils.loadImageNetsVideo(mContext, item.topicImg!!, helper.getView(R.id.iv_logo))
  }
}
