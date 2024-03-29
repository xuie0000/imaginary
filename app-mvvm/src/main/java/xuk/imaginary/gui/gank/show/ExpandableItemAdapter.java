package xuk.imaginary.gui.gank.show;

import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.app.App;
import xuk.imaginary.gui.web.WebViewActivity;
import xuk.imaginary.util.GlideUtils;

/**
 * @author xuie
 * @date 17-8-15
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/app/src/main/java/com/chad/baserecyclerviewadapterhelper/adapter/ExpandableItemAdapter.java
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = "ExpandableItemAdapter";
    static final int TYPE_LEVEL_0 = 0;
    static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, android.R.layout.test_list_item);
        addItemType(TYPE_LEVEL_1, R.layout.item_activity_gank_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
            default:
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
//                helper.setText(R.id.articleName, lv1.getArticleName())
                helper.setText(R.id.articleName, Html.fromHtml(webLink));
                helper.getView(R.id.articleName).setOnClickListener(v -> {
                    Log.d(TAG, "onClick...");
                    Intent intent = new Intent(App.getContext(), WebViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", lv1.getArticleUrl());
                    App.getContext().startActivity(intent);
                });

                helper.setText(R.id.author, String.format("(%s)", lv1.getAuthor()));

                if (lv1.getImageUrl() != null) {
                    GlideUtils.loadImageMeizhiDetail(mContext, lv1.getImageUrl(), helper.getView(R.id.thumb));
                }
                break;
        }

    }
}
