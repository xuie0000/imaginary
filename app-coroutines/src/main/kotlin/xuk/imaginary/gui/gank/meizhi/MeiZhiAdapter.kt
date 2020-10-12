package xuk.imaginary.gui.gank.meizhi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xuk.imaginary.R
import xuk.imaginary.common.gkSwitchDate
import xuk.imaginary.common.loadImage
import xuk.imaginary.data.GkIo
import xuk.imaginary.gui.gank.show.GkActivity

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class MeiZhiAdapter : ListAdapter<GkIo.BaseBean, MeiZhiAdapter.MeiZhiViewHolder>(MeiZhiDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeiZhiViewHolder {
    return MeiZhiViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_fragment_meizhi, parent, false))
  }

  override fun onBindViewHolder(holder: MeiZhiViewHolder, position: Int) {
    with(holder) {
      bind(getItem(position))
    }
  }

  inner class MeiZhiViewHolder internal constructor(view: View)
    : RecyclerView.ViewHolder(view) {

    fun bind(bb: GkIo.BaseBean) {
      with(itemView) {
        findViewById<TextView>(R.id.date).text = bb.desc
        findViewById<ImageView>(R.id.meizhi).loadImage(bb.url)

        setOnClickListener {
          val intent = Intent(it.context, GkActivity::class.java).apply {
            putExtra("date", bb.publishedAt.gkSwitchDate())
            putExtra("image", bb.url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
          }
//          val options = ActivityOptions.makeSceneTransitionAnimation(it.context, it, "shareObject")
//          ActivityCompat.startActivity(it.context, intent, options.toBundle())
          it.context.startActivity(intent)

        }
      }
    }
  }

  private class MeiZhiDiffCallback : DiffUtil.ItemCallback<GkIo.BaseBean>() {

    override fun areItemsTheSame(oldItem: GkIo.BaseBean, newItem: GkIo.BaseBean): Boolean {
      return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: GkIo.BaseBean, newItem: GkIo.BaseBean): Boolean {
      return oldItem.url == newItem.url
    }
  }
}
