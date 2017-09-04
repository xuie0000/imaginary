package com.xuie.imaginaryandroid.gui.news;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.glide.GlideApp;

import java.util.List;

/**
 * Created by xuie on 17-7-5.
 */

public class NewsListAdapter extends BaseQuickAdapter<NetsSummary, BaseViewHolder> {
    private static final String TAG = "NewsListAdapter";

    public NewsListAdapter(List<NetsSummary> netsSummaries) {
        super(R.layout.item_fragment_news, netsSummaries);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetsSummary item) {
        helper.setText(R.id.ltitle, item.getTitle())
                .setText(R.id.ptime, item.getPtime())
                .setText(R.id.digest, item.getDigest());
        GlideApp.with(mContext).load(item.getImgsrc()).into((ImageView) helper.getView(R.id.img_src));
    }
}
