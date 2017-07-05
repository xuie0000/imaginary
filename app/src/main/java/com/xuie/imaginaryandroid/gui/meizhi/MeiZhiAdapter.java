package com.xuie.imaginaryandroid.gui.meizhi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.MeiZhi;

import java.util.List;

/**
 * Created by xuie on 17-7-5.
 */

public class MeiZhiAdapter extends BaseQuickAdapter<MeiZhi, BaseViewHolder> {

    public MeiZhiAdapter(List<MeiZhi> meiZhis) {
        super(R.layout.item_meizhi, meiZhis);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZhi item) {

    }
}
