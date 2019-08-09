package xuk.imaginary.gui.gank.show

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.imaginary.R
import xuk.imaginary.app.App
import xuk.imaginary.gui.web.WebViewActivity
import xuk.imaginary.util.GlideUtils

/**
 * @author Jie Xu
 * @date 17-8-15
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/app/src/main/java/com/chad/baserecyclerviewadapterhelper/adapter/ExpandableItemAdapter.java
 */
class ExpandableItemAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
internal constructor(data: List<MulItem>) : BaseMultiItemQuickAdapter<MulItem, BaseViewHolder>(data) {

  init {
    addItemType(TYPE_LEVEL_0, android.R.layout.test_list_item)
    addItemType(TYPE_LEVEL_1, R.layout.item_activity_gank_content)
  }

  override fun convert(helper: BaseViewHolder, item: MulItem) {
    when (helper.itemViewType) {
      TYPE_LEVEL_0 -> {
        val lv0 = item as Level0Item
        helper.setText(android.R.id.text1, lv0.type)
        // 默认展开二级 https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/2174
        helper.itemView.setOnClickListener {
          val pos = helper.adapterPosition
          if (lv0.isExpanded) {
            collapse(pos)
          } else {
            expand(pos)
          }
        }
      }
      TYPE_LEVEL_1 -> {
        val lv1 = item as Level1Item
        Log.d(TAG, "v1:" + lv1.articleName)
        val webLink = String.format("<a href=\\'%s\\'> %s</a>",
            lv1.articleUrl, lv1.articleName)
        //helper.setText(R.id.articleName, lv1.getArticleName())
        helper.setText(R.id.articleName, Html.fromHtml(webLink))
        helper.getView<View>(R.id.articleName).setOnClickListener {
          val intent = Intent(App.context, WebViewActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
          intent.putExtra("url", lv1.articleUrl)
          App.context!!.startActivity(intent)
        }

        helper.setText(R.id.author, String.format("(%s)", lv1.author))

        if (lv1.imageUrl != null) {
          GlideUtils.loadImageMeizhiDetail(mContext, lv1.imageUrl!!, helper.getView(R.id.thumb))
        }
      }
      else -> {
        val lv0 = item as Level0Item
        Log.d(TAG, "vx: ${lv0.type}")
        helper.setText(android.R.id.text1, lv0.type)
      }
    }
  }

  companion object {
    private const val TAG = "ExpandableItemAdapter"
    internal const val TYPE_LEVEL_0 = 0
    internal const val TYPE_LEVEL_1 = 1
  }
}
