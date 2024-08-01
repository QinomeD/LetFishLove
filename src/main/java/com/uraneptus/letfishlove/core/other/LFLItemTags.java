package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LFLItemTags {
    public static final TagKey<Item> COD = create("cod");
    public static final TagKey<Item> SALMON = create("salmon");
    public static final TagKey<Item> PUFFERFISH = create("pufferfish");
    public static final TagKey<Item> TROPICAL_FISH = create("tropical_fish");
    public static final TagKey<Item> PIKE = create("upgrade_aquatic", "pike");
    public static final TagKey<Item> PERCH = create("upgrade_aquatic", "perch");
    public static final TagKey<Item> LIONFISH = create("upgrade_aquatic", "lionfish");
    public static final TagKey<Item> BASS = create("naturalist", "bass");
    public static final TagKey<Item> CATFISH = create("naturalist", "catfish");
    public static final TagKey<Item> KOI = create("environmental", "koi");

    public static TagKey<Item> create(String fishName) {
        return create("minecraft", fishName);
    }

    public static TagKey<Item> create(String modid, String fishName) {
        return TagKey.create(Registry.ITEM_REGISTRY, LetFishLoveMod.modPrefix("fish_food/" + modid + "/" + fishName));
    }
}
