package com.uraneptus.letfishlove.data.server.tags;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.RoeBlock;
import com.uraneptus.letfishlove.core.other.LFLBlockTags;
import com.uraneptus.letfishlove.core.other.LFLEntityTags;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class LFLEntityTagsProvider extends EntityTypeTagsProvider {

    public LFLEntityTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags() {
        tag(LFLEntityTags.BREEDABLE_FISH).add(EntityType.COD, EntityType.SALMON, EntityType.PUFFERFISH, EntityType.TROPICAL_FISH);
    }
}
