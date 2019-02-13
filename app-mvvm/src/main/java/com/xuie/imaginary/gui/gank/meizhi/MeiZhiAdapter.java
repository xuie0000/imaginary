package com.xuie.imaginary.gui.gank.meizhi;

import com.xuie.imaginary.R;
import com.xuie.imaginary.base.BaseDataBindingAdapter;
import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.databinding.ItemFragmentMeizhiBinding;

import java.util.List;

/**
 * @author xuie
 * @date 17-7-5
 */
public class MeiZhiAdapter extends BaseDataBindingAdapter<BaseBean, ItemFragmentMeizhiBinding> {
    MeiZhiAdapter(List<BaseBean> meiZhis) {
        super(R.layout.item_fragment_meizhi, meiZhis);
    }

    @Override
    protected void convert(ItemFragmentMeizhiBinding binding, BaseBean item) {
        binding.setData(item);
    }
}