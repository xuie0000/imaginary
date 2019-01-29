package com.xuie.imaginary.gui.gank.meizhi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xuie.imaginary.BuildConfig;
import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.NetsSummary;
import com.xuie.imaginary.gui.gank.show.ExpandableItemAdapter;
import com.xuie.imaginary.gui.nets.news.NewsListAdapter;
import com.xuie.imaginary.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class MeizhiBindings {
    private static final String TAG = "MeizhiBindings";

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<BaseBean> items) {
        MeiZhiAdapter adapter = (MeiZhiAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter("multiItems")
    public static void setMultiItems(RecyclerView recyclerView, List<MultiItemEntity> items) {
        ExpandableItemAdapter adapter = (ExpandableItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            if (items == null) {
                adapter.replaceData(new ArrayList<>());
            } else {
                adapter.replaceData(items);
            }
            adapter.expandAll();
        }
    }

    @BindingAdapter("netsItems")
    public static void setNetsItems(RecyclerView recyclerView, List<NetsSummary> items) {
        NewsListAdapter adapter = (NewsListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Context context = view.getContext();
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, "loadImage: url is null");
            return;
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, url);
        }

        GlideUtils.loadImageMeizhi(context, url, view);
    }

    @BindingAdapter({"netsFormat", "text1", "text2"})
    public static void setFormattedText(TextView textView, String netsFormat, String text1, String text2) {
        textView.setText(String.format(netsFormat, text1, text2));
    }
}
