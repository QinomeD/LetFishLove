package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class LFLBlockTags {
    public static final TagKey<Block> COD = create("cod");
    public static final TagKey<Block> SALMON = create("salmon");
    public static final TagKey<Block> PUFFERFISH = create("pufferfish");
    public static final TagKey<Block> TROPICAL_FISH = create("tropical_fish");
    public static final TagKey<Block> PIKE = create("upgrade_aquatic", "pike");
    public static final TagKey<Block> PERCH = create("upgrade_aquatic", "perch");
    public static final TagKey<Block> LIONFISH = create("upgrade_aquatic", "lionfish");
    public static final TagKey<Block> BASS = create("naturalist", "bass");
    public static final TagKey<Block> CATFISH = create("naturalist", "catfish");
    public static final TagKey<Block> KOI = create("environmental", "koi");

    public static TagKey<Block> create(String fishName) {
        return create("minecraft", fishName);
    }

    public static TagKey<Block> create(String modid, String fishName) {
        return TagKey.create(Registry.BLOCK_REGISTRY, LetFishLoveMod.modPrefix("fish_roe/" + modid + "/" + fishName));
    }
}
