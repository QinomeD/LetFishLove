package com.uraneptus.letfishlove.core.other;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class LFLEntityTags {
    public static final TagKey<EntityType<?>> BREEDABLE_FISH = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, LetFishLoveMod.modPrefix("breedable_fish"));
}
