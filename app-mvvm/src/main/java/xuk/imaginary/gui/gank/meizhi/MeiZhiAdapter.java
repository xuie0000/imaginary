package xuk.imaginary.gui.gank.meizhi;

import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.base.BaseDataBindingAdapter;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.databinding.ItemFragmentMeizhiBinding;

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
