package com.xuie.imaginary.gui.gank.meizhi;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.util.GlideUtils;

import java.util.List;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class MeizhiListBindings {

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<BaseBean> items) {
        MeiZhiAdapter adapter = (MeiZhiAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Context context = view.getContext();
        GlideUtils.loadImageMeizhi(context, url, view);
    }
}
