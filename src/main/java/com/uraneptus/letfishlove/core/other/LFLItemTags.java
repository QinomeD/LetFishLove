package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LFLItemTags {
    public static final TagKey<Item> COD = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/cod"));
    public static final TagKey<Item> SALMON = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/salmon"));
    public static final TagKey<Item> PUFFERFISH = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/pufferfish"));
    public static final TagKey<Item> TROPICAL_FISH = TagKey.create(Registries.ITEM, LetFishLoveMod.modPrefix("fish_food/tropical_fish"));
}
