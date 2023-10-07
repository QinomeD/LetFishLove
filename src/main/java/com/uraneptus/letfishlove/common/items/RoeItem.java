package com.uraneptus.letfishlove.common.items;

import com.uraneptus.letfishlove.core.other.LFLProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RoeItem extends BlockItem {

    public RoeItem(Block pBlock) {
        super(pBlock, LFLProperties.roeItemProperties());
    }

    //TODO Add place on water interaction
}
