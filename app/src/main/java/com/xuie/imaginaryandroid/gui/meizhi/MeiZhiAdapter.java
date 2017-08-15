package com.xuie.imaginaryandroid.gui.meizhi;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.福利Bean;

import java.util.List;

/**
 * Created by xuie on 17-7-5.
 */

public class MeiZhiAdapter extends BaseQuickAdapter<福利Bean, BaseViewHolder> {
    private static final String TAG = "MeiZhiAdapter";

    public MeiZhiAdapter(List<福利Bean> meiZhis) {
        super(R.layout.item_fragment_gank_meizhi, meiZhis);
    }

    @Override
    protected void convert(BaseViewHolder helper, 福利Bean item) {
        helper.setText(R.id.date, item.getDesc());
        Glide.with(mContext).load(item.getUrl())/*.crossFade()*/.into((ImageView) helper.getView(R.id.meizhi));
    }
}
