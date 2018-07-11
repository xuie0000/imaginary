package com.xuie.imaginaryandroid.gui.nets.news;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.util.GlideUtils;

import java.util.List;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<NetsSummary, BaseViewHolder> {
    private static final String TAG = "NewsListAdapter";

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(List<NetsSummary> data) {
        super(data);
        addItemType(NetsSummary.IMG_ONE, R.layout.item_fragment_news);
        addItemType(NetsSummary.IMG_MULTI, R.layout.item_fragment_news_multi);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetsSummary item) {
        switch (helper.getItemViewType()) {
            case NetsSummary.IMG_ONE:
            default:
                helper.setText(R.id.ltitle, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest());
                GlideUtils.loadImageNetsList(mContext, item.getImgsrc(), helper.getView(R.id.img_src));
                break;
            case NetsSummary.IMG_MULTI:
                helper.setText(R.id.ltitle, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest());
                RecyclerView rv = helper.getView(R.id.img_src);
                rv.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
                SimpleAdapter sa = new SimpleAdapter(item.getImgextra());
                rv.setAdapter(sa);
                break;
        }
    }

    private class SimpleAdapter extends BaseQuickAdapter<NetsSummary.ImgextraBean, BaseViewHolder> {
        public SimpleAdapter(@Nullable List<NetsSummary.ImgextraBean> data) {
            super(R.layout.item_fragment_news_child, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NetsSummary.ImgextraBean item) {
            GlideUtils.loadImageNetsList(mContext, item.getImgsrc(), helper.getView(R.id.iv_image));
        }
    }
}
