package xuk.imaginary.base

import android.view.View

import androidx.databinding.ViewDataBinding

import com.chad.library.adapter.base.BaseViewHolder

/**
 * Date: 2017/5/11 14:42.
 * Email: tyshengsx@gmail.com
 *
 * @author tysheng
 */
class BaseBindingViewHolder<Binding : ViewDataBinding>(view: View) : BaseViewHolder(view) {
  var binding: Binding? = null
}
