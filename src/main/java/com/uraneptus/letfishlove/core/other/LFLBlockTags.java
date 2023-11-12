package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class LFLBlockTags {
    public static final TagKey<Block> COD = TagKey.create(Registries.BLOCK, LetFishLoveMod.modPrefix("fish_roe/cod"));
    public static final TagKey<Block> SALMON = TagKey.create(Registries.BLOCK, LetFishLoveMod.modPrefix("fish_roe/salmon"));
    public static final TagKey<Block> PUFFERFISH = TagKey.create(Registries.BLOCK, LetFishLoveMod.modPrefix("fish_roe/pufferfish"));
    public static final TagKey<Block> TROPICAL_FISH = TagKey.create(Registries.BLOCK, LetFishLoveMod.modPrefix("fish_roe/tropical_fish"));
}
