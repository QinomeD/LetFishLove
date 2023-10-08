package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTags {
    public static final TagKey<Item> COD = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/cod"));
    public static final TagKey<Item> SALMON = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/salmon"));
    public static final TagKey<Item> PUFFERFISH = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/pufferfish"));
    public static final TagKey<Item> TROPICAL_FISH = TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/tropical_fish"));
}
