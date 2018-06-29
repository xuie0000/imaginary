package com.xuie.imaginaryandroid.gui.gank.meizhi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.util.GlideUtils;

import java.util.List;

/**
 * @author xuie
 * @date 17-7-5
 */
public class MeiZhiAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {
    private static final String TAG = "MeiZhiAdapter";

    public MeiZhiAdapter(List<BaseBean> meiZhis) {
        super(R.layout.item_fragment_meizhi, meiZhis);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        helper.setText(R.id.date, item.getDesc());
        GlideUtils.loadImage(mContext, item.getUrl(), helper.getView(R.id.meizhi));
    }
}
