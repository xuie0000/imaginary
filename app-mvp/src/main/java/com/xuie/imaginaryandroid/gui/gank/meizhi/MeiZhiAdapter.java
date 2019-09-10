package xuk.imaginary.gui.gank.meizhi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import xuk.imaginary.R;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.common.GlideUtils;

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
        GlideUtils.loadImageMeizhi(mContext, item.getUrl(), helper.getView(R.id.meizhi));
    }
}
