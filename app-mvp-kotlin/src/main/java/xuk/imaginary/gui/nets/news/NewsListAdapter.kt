package xuk.imaginary.gui.nets.news

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xuk.imaginary.R
import xuk.imaginary.data.ImgextraBean
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.util.GlideUtils

/**
 * Created by xuie on 17-7-5.
 */

class NewsListAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(data: List<NetsSummary>?) : BaseMultiItemQuickAdapter<NetsSummary, BaseViewHolder>(data) {

  init {
    addItemType(NetsSummary.IMG_ONE, R.layout.item_fragment_news)
    addItemType(NetsSummary.IMG_MULTI, R.layout.item_fragment_news_multi)
  }

  override fun convert(helper: BaseViewHolder, item: NetsSummary) {
    when (helper.itemViewType) {
      NetsSummary.IMG_ONE -> {
        helper.setText(R.id.ltitle, item.title)
            .setText(R.id.source, item.source)
            .setText(R.id.digest, item.digest)
        GlideUtils.loadImageNetsList(mContext, item.imgsrc, helper.getView(R.id.img_src))
      }
      NetsSummary.IMG_MULTI -> {
        helper.setText(R.id.ltitle, item.title)
            .setText(R.id.source, item.source)
            .setText(R.id.digest, item.digest)
        val rv = helper.getView<RecyclerView>(R.id.img_src)

        rv.layoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL,
            false)
        val sa = SimpleAdapter(item.imgextra)
        rv.adapter = sa
      }
    }
  }

  private inner class SimpleAdapter(data: List<ImgextraBean>) :
      BaseQuickAdapter<ImgextraBean, BaseViewHolder>(R.layout.item_fragment_news_child, data) {

    override fun convert(helper: BaseViewHolder, item: ImgextraBean) {
      GlideUtils.loadImageNetsList(mContext, item.imgsrc, helper.getView(R.id.iv_image))
    }
  }

  companion object {
    private val TAG = "NewsListAdapter"
  }
}
