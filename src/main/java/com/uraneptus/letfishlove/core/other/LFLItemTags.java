package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LFLItemTags {
    public static final TagKey<Item> COD = create("cod");
    public static final TagKey<Item> SALMON = create("salmon");
    public static final TagKey<Item> PUFFERFISH = create("pufferfish");
    public static final TagKey<Item> TROPICAL_FISH = create("tropical_fish");

    public static TagKey<Item> create(String fishName) {
        return TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/minecraft/" + fishName));
    }
}
