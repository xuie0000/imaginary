package xuk.imaginary.base

import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Date: 2017/5/11 14:39.
 * Email: tyshengsx@gmail.com
 *
 * @author tysheng
 */
abstract class BaseDataBindingAdapter<T, Binding : ViewDataBinding> : BaseQuickAdapter<T, BaseBindingViewHolder<Binding>> {

  constructor(@LayoutRes layoutResId: Int, data: List<T>?) : super(layoutResId, data) {}

  constructor(data: List<T>?) : super(data) {}

  constructor(@LayoutRes layoutResId: Int) : super(layoutResId) {}

  override fun createBaseViewHolder(view: View): BaseBindingViewHolder<Binding> {
    return BaseBindingViewHolder(view)
  }

  override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BaseBindingViewHolder<Binding> {
    val binding = DataBindingUtil.inflate<Binding>(mLayoutInflater, layoutResId, parent, false)
    val view: View
    if (binding == null) {
      view = getItemView(layoutResId, parent)
    } else {
      view = binding.root
    }
    val holder = BaseBindingViewHolder<Binding>(view)
    holder.binding = binding
    return holder
  }

  override fun convert(helper: BaseBindingViewHolder<Binding>, item: T) {
    convert(helper.binding, item)
    helper.binding!!.executePendingBindings()
  }

  protected abstract fun convert(binding: Binding?, item: T)
}
