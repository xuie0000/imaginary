package com.xuie.imaginaryandroid.gui.meizhi;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.glide.GlideApp;

import java.util.List;

/**
 * Created by xuie on 17-7-5.
 */

public class MeiZhiAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {
    private static final String TAG = "MeiZhiAdapter";

    public MeiZhiAdapter(List<BaseBean> meiZhis) {
        super(R.layout.item_fragment_meizhi, meiZhis);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.date, item.getDesc());
        GlideApp.with(mContext)
                .load(item.getUrl())
                .placeholder(R.mipmap.meizhi_sample)
                .dontTransform()
                .into((ImageView) helper.getView(R.id.meizhi));
    }
}
