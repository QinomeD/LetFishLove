package com.uraneptus.letfishlove.core.other;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LFLProperties {
    //BLOCKS
    public static BlockBehaviour.Properties roeBlockProperties() {
        return BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).requiresCorrectToolForDrops();
    }

    //ITEMS
    public static Item.Properties roeItemProperties() {
        return new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(16).food(new FoodProperties.Builder().nutrition(1).build());
    }
}
