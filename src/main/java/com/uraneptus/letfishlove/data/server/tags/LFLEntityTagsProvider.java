package com.uraneptus.letfishlove.data.server.tags;

import com.starfish_studios.naturalist.registry.NaturalistEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.upgrade_aquatic.core.registry.UAEntityTypes;
import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.other.LFLEntityTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class LFLEntityTagsProvider extends EntityTypeTagsProvider {

    public LFLEntityTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags() {
        tag(LFLEntityTags.BREEDABLE_FISH).add(EntityType.COD, EntityType.SALMON, EntityType.PUFFERFISH, EntityType.TROPICAL_FISH);

        addOptional(LFLEntityTags.BREEDABLE_FISH,
                UAEntityTypes.PIKE.get(), UAEntityTypes.PERCH.get(), UAEntityTypes.LIONFISH.get(),
                NaturalistEntityTypes.BASS.get(), NaturalistEntityTypes.CATFISH.get(),
                EnvironmentalEntityTypes.KOI.get());
    }

    private void addOptional(TagKey<EntityType<?>> tagKey, EntityType<?>... fish) {
        for (var singularFish : fish) {
            tag(tagKey).addOptional(ForgeRegistries.ENTITY_TYPES.getKey(singularFish));
        }
    }
}
