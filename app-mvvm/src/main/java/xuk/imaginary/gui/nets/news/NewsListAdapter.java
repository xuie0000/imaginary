package xuk.imaginary.gui.nets.news;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.util.GlideUtils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<NetsSummary, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    NewsListAdapter(List<NetsSummary> data) {
        super(data);
        addItemType(NetsSummary.IMG_ONE, R.layout.item_fragment_news);
        addItemType(NetsSummary.IMG_MULTI, R.layout.item_fragment_news_multi);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetsSummary item) {
        switch (helper.getItemViewType()) {
            case NetsSummary.IMG_ONE:
            default:
                helper.setText(R.id.title, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest());
                GlideUtils.loadImageNetsList(mContext, item.getImgsrc(), helper.getView(R.id.img_src));
                break;
            case NetsSummary.IMG_MULTI:
                helper.setText(R.id.title, item.getTitle())
                        .setText(R.id.source, item.getSource())
                        .setText(R.id.digest, item.getDigest());
                RecyclerView rv = helper.getView(R.id.img_src);
                rv.setLayoutManager(new GridLayoutManager(mContext, 2, RecyclerView.VERTICAL, false));
                SimpleAdapter sa = new SimpleAdapter(item.getImgextra());
                rv.setAdapter(sa);
                break;
        }
    }

    private class SimpleAdapter extends BaseQuickAdapter<NetsSummary.ImgextraBean, BaseViewHolder> {
        SimpleAdapter(@Nullable List<NetsSummary.ImgextraBean> data) {
            super(R.layout.item_fragment_news_child, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NetsSummary.ImgextraBean item) {
            GlideUtils.loadImageNetsList(mContext, item.getImgsrc(), helper.getView(R.id.iv_image));
        }
    }
}
