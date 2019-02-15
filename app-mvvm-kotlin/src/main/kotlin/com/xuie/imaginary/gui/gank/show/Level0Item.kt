package com.xuie.imaginary.gui.gank.show

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author Jie Xu
 * @date 17-8-15
 */
class Level0Item : AbstractExpandableItem<Level1Item>(), MultiItemEntity {
  var type: String? = null

  override fun getLevel(): Int {
    return 0
  }

  override fun getItemType(): Int {
    return ExpandableItemAdapter.TYPE_LEVEL_0
  }
}
