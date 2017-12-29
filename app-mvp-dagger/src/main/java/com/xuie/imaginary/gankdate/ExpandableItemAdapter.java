package com.xuie.imaginary.gankdate;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xuie.imaginary.R;
import com.xuie.imaginary.ToDoApplication;
import com.xuie.imaginary.glide.GlideApp;
import com.xuie.imaginary.web.WebViewActivity;

import java.util.List;

/**
 * Created by xuie on 17-8-15.
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/app/src/main/java/com/chad/baserecyclerviewadapterhelper/adapter/ExpandableItemAdapter.java
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = "ExpandableItemAdapter";
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, android.R.layout.test_list_item);
        addItemType(TYPE_LEVEL_1, R.layout.item_activity_gank_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                helper.setText(android.R.id.text1, lv0.getType());
                // 展开与闭合
//                helper.itemView.setOnClickListener(v -> {
//                    int pos = helper.getAdapterPosition();
//                    if (lv0.isExpanded()) {
//                        collapse(pos);
//                    } else {
//                        expand(pos);
//                    }
//                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                String webLink = String.format("<a href=\\'%s\\'> %s</a>",
                        lv1.getArticleUrl(), lv1.getArticleName());
//                helper.setText(R.id.articleName, lv1.getArticleName());
                helper.setText(R.id.articleName, Html.fromHtml(webLink/*, Html.FROM_HTML_MODE_LEGACY*/));
                helper.getView(R.id.articleName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick...");
                        Intent intent = new Intent(ToDoApplication.getContext(), WebViewActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("url", lv1.getArticleUrl());
                        ToDoApplication.getContext().startActivity(intent);
                    }
                });

                // 添加TextView自动打开超链接，失败
//                TextView tvArticle = helper.getView(R.id.articleName);
//                tvArticle.setText(Html.fromHtml(webLink/*, Html.FROM_HTML_MODE_LEGACY*/));
//                tvArticle.setMovementMethod(LinkMovementMethod.getInstance());
                helper.setText(R.id.author, String.format("(%s)", lv1.getAuthor()));
                // 添加Child Click，不过无效
//                helper.addOnClickListener(R.id.articleName);

                if (lv1.getImageUrl() != null)
                    GlideApp.with(mContext)
                            .load(lv1.getImageUrl())
                            .into((ImageView) helper.getView(R.id.thumb));
                break;
        }

    }
}
