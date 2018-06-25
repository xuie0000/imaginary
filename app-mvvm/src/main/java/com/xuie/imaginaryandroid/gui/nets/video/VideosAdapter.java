package com.xuie.imaginaryandroid.gui.nets.video;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.VideoBean;
import com.xuie.imaginaryandroid.glide.GlideApp;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author xuie
 * @date 17-8-22
 */
public class VideosAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {
    private static final String TAG = "VideosAdapter";

    public VideosAdapter(List<VideoBean> videoBeen) {
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
        GlideApp.with(mContext).load(item.getCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.mipmap.ic_empty_picture)
                .into(jcVideoPlayerStandard.thumbImageView);
        GlideApp.with(mContext).load(item.getTopicImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_empty_picture)
                .centerCrop()
                /*.transform(new GlideRoundTransformUtil(context))*/
                .into((ImageView) helper.getView(R.id.iv_logo));
    }
}
