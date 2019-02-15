package com.xuie.imaginary

import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.xuie.imaginary.data.BaseBean
import com.xuie.imaginary.data.NetsDetail
import com.xuie.imaginary.data.NetsSummary
import com.xuie.imaginary.data.VideoBean
import com.xuie.imaginary.gui.gank.meizhi.MeiZhiAdapter
import com.xuie.imaginary.gui.gank.show.ExpandableItemAdapter
import com.xuie.imaginary.gui.nets.news.NewsListAdapter
import com.xuie.imaginary.gui.nets.video.VideosAdapter
import com.xuie.imaginary.util.GlideUtils
import com.xuie.imaginary.widget.UrlImageGetter
import java.util.*

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
object ImaginaryBindings {
  private const val TAG = "ImaginaryBindings"

  @BindingAdapter("items")
  fun setItems(recyclerView: RecyclerView, items: List<BaseBean>) {
    val adapter = recyclerView.adapter as MeiZhiAdapter?
    adapter?.replaceData(items)
  }

  @BindingAdapter("multiItems")
  fun setMultiItems(recyclerView: RecyclerView, items: List<MultiItemEntity>?) {
    val adapter = recyclerView.adapter as ExpandableItemAdapter?
    if (adapter != null) {
      if (items == null) {
        adapter.replaceData(ArrayList())
      } else {
        adapter.replaceData(items)
      }
      adapter.expandAll()
    }
  }

  @BindingAdapter("netsItems")
  fun setNetsItems(recyclerView: RecyclerView, items: List<NetsSummary>) {
    val adapter = recyclerView.adapter as NewsListAdapter?
    adapter?.replaceData(items)
  }

  @BindingAdapter("videosItems")
  fun setVideosItems(recyclerView: RecyclerView, items: List<VideoBean>) {
    val adapter = recyclerView.adapter as VideosAdapter?
    adapter?.replaceData(items)
  }

  @BindingAdapter("imageUrl")
  fun loadImage(view: ImageView, url: String) {
    val context = view.context
    if (TextUtils.isEmpty(url)) {
      Log.d(TAG, "loadImage: url is null")
      return
    }

    if (BuildConfig.DEBUG) {
      Log.d(TAG, url)
    }

    GlideUtils.loadImageMeizhi(context, url, view)
  }

  @BindingAdapter("showNetsBody")
  fun showNetsDetailBody(view: TextView, netsDetail: NetsDetail?) {
    if (netsDetail?.img == null) {
      Log.e(TAG, "showNetsDetailBody: null")
      return
    }
    val imgTotal = netsDetail.img!!.size
    val body = netsDetail.body
    if (isShowBody(netsDetail.body, imgTotal)) {
      val urlImageGetter = UrlImageGetter(view, body!!, imgTotal)
      view.text = Html.fromHtml(body, urlImageGetter, null)
    } else {
      view.text = Html.fromHtml(body)
    }
  }

  private fun isShowBody(newsBody: String?, imgTotal: Int): Boolean {
    return imgTotal >= 2 && newsBody != null
  }

  @BindingAdapter("netsFormat", "text1", "text2")
  fun setFormattedText(textView: TextView, netsFormat: String, text1: String, text2: String) {
    textView.text = String.format(netsFormat, text1, text2)
  }
}
