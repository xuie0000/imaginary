package xuk.imaginary.gui.gank.show

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import xuk.imaginary.R
import xuk.imaginary.data.GkIo
import xuk.imaginary.common.isNotEmptyOrBlank
import xuk.imaginary.common.loadImage
import xuk.imaginary.data.MeiZhi

class GkAdapter : ListAdapter<MeiZhi, BaseViewHolder>(GkDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    return when (viewType) {
      MeiZhi.TYPE_TITLE -> {
        TitleHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.mei_zhi_title_item, parent, false))
      }
      else -> {
        ContentHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.mei_zhi_content_item, parent, false))
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return getItem(position).type
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    with(holder) {
      bind(getItem(position))
    }
  }

  inner class TitleHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bind(mz: MeiZhi) {
      itemView.findViewById<TextView>(R.id.tvTitle).text = mz.title
    }
  }

  inner class ContentHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bind(mz: MeiZhi) {
      val gk = mz.gk
      val webLink = String.format("<a href=\\'%s\\'> %s</a>", gk?.url, gk?.desc)
      itemView.findViewById<TextView>(R.id.tvArticleName).text = Html.fromHtml(webLink)
      itemView.findViewById<TextView>(R.id.tvAuthor).text = gk?.who
      gk?.images?.isNotEmptyOrBlank().let {
        if (it?.compareTo(true) == 0) {
          itemView.findViewById<ImageView>(R.id.ivThumb).loadImage(gk?.images?.get(0)!!)
        }
      }
    }
  }

  private class GkDiffCallback : DiffUtil.ItemCallback<MeiZhi>() {

    override fun areItemsTheSame(oldItem: MeiZhi, newItem: MeiZhi): Boolean {
      return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: MeiZhi, newItem: MeiZhi): Boolean {
      return oldItem.gk?._id == newItem.gk?._id
    }
  }

}
