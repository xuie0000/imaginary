package xuk.imaginary.gui.gank.show

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author Jie Xu
 * @date 17-8-15
 */
abstract class MulItem : AbstractExpandableItem<Level1Item>(), MultiItemEntity

class Level0Item : MulItem() {
  var type: String? = null

  override fun getLevel(): Int {
    return 0
  }

  override fun getItemType(): Int {
    return ExpandableItemAdapter.TYPE_LEVEL_0
  }

}

class Level1Item : MulItem() {
  var articleName: String? = null
  var articleUrl: String? = null
  var author: String? = null
  var imageUrl: String? = null

  override fun getLevel(): Int {
    return 1
  }

  override fun getItemType(): Int {
    return ExpandableItemAdapter.TYPE_LEVEL_1
  }
}

