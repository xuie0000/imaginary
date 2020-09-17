package xuk.imaginary.gui.gank.meizhi

import com.xuie.imaginary.databinding.ItemFragmentMeizhiBinding
import xuk.imaginary.R
import xuk.imaginary.base.BaseDataBindingAdapter
import xuk.imaginary.data.BaseBean

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class MeiZhiAdapter internal constructor(meiZhis: List<BaseBean>) :
    BaseDataBindingAdapter<BaseBean, ItemFragmentMeizhiBinding>(R.layout.item_fragment_meizhi, meiZhis) {

  override fun convert(binding: ItemFragmentMeizhiBinding?, item: BaseBean) {
    binding!!.data = item
  }
}
