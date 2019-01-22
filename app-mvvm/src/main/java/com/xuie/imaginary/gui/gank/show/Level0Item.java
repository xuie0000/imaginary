package com.xuie.imaginary.gui.gank.show;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author xuie
 * @date 17-8-15
 */
public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
}
