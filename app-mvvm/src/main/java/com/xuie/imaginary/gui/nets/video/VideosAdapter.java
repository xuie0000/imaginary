package com.xuie.imaginary.gui.nets.video;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginary.R;
import com.xuie.imaginary.data.VideoBean;
import com.xuie.imaginary.util.GlideUtils;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author xuie
 * @date 17-8-22
 */
public class VideosAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    VideosAdapter(List<VideoBean> videoBeen) {
        super(R.layout.item_video_list, videoBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.tv_from, item.getTopicName());
        helper.setText(R.id.tv_play_time,
                String.format(mContext.getResources().getString(R.string.video_play_times),
                        String.valueOf(item.getPlayCount())
                )
        );
        JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(
                item.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty(item.getDescription()) ? item.getTitle() + "" : item.getDescription());
        GlideUtils.loadImageNetsVideo(mContext, item.getCover(), jcVideoPlayerStandard.thumbImageView);
        GlideUtils.loadImageNetsVideo(mContext, item.getTopicImg(), helper.getView(R.id.iv_logo));
    }
}