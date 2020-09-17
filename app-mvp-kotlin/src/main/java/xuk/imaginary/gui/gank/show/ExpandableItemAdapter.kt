package xuk.imaginary.gui.gank.show

import android.content.Intent
import android.text.Html
import android.util.Log
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import xuk.imaginary.R
import xuk.imaginary.app.App
import xuk.imaginary.gui.web.WebViewActivity
import xuk.imaginary.util.GlideUtils

/**
 * Created by xuie on 17-8-15.
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/app/src/main/java/com/chad/baserecyclerviewadapterhelper/adapter/ExpandableItemAdapter.java
 */

class ExpandableItemAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(data: List<MultiItemEntity>?) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

  init {
    addItemType(TYPE_LEVEL_0, android.R.layout.test_list_item)
    addItemType(TYPE_LEVEL_1, R.layout.item_activity_gank_content)
  }

  override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
    when (helper.itemViewType) {
      TYPE_LEVEL_0 -> {
        val lv0 = item as Level0Item
        helper.setText(android.R.id.text1, lv0.type)
        // 展开与闭合
//                helper.itemView.setOnClickListener({
//                    val pos = helper.adapterPosition
//                    if (lv0.isExpanded) {
//                        collapse(pos)
//                    } else {
//                        expand(pos)
//                    }
//                })
      }
      TYPE_LEVEL_1 -> {
        val lv1 = item as Level1Item
        val webLink = String.format("<a href=\\'%s\\'> %s</a>",
            lv1.articleUrl, lv1.articleName)
//                helper.setText(R.id.articleName, lv1.articleName)
        helper.setText(R.id.articleName, Html.fromHtml(webLink/*, Html.FROM_HTML_MODE_LEGACY*/))
        helper.getView<TextView>(R.id.articleName).setOnClickListener {
          Log.d(TAG, "onClick...")
          val intent = Intent(App.context, WebViewActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
          intent.putExtra("url", lv1.articleUrl)
          App.context.startActivity(intent)
        }

        // 添加TextView自动打开超链接，失败
//                TextView tvArticle = helper . getView (R.id.articleName);
//                tvArticle.setText(Html.fromHtml(webLink/*, Html.FROM_HTML_MODE_LEGACY*/));
//                tvArticle.setMovementMethod(LinkMovementMethod.getInstance());
        helper.setText(R.id.author, String.format("(%s)", lv1.author))
        // 添加Child Click，不过无效
//                helper.addOnClickListener(R.id.articleName);

        if (lv1.imageUrl != null)
          GlideUtils.loadImageMeizhiDetail(mContext, lv1.imageUrl, helper.getView(R.id.thumb))

      }
    }

  }

  companion object {
    private const val TAG = "ExpandableItemAdapter"
    const val TYPE_LEVEL_0 = 0
    const val TYPE_LEVEL_1 = 1
  }
}
