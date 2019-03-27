package com.xuie.imaginary.gui.gank.show

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author Jie Xu
 * @date 17-8-15
 */
class Level1Item : AbstractExpandableItem<Level1Item>(), MultiItemEntity {
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
