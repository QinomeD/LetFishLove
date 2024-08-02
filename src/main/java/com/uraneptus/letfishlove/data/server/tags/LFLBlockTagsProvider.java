package com.uraneptus.letfishlove.data.server.tags;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.other.LFLBlockTags;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class LFLBlockTagsProvider extends BlockTagsProvider {

    public LFLBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags() {
        LFLBlocks.BLOCKS.getEntries().forEach(block -> tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block.get()));

        tag(LFLBlockTags.COD).add(LFLBlocks.COD_ROE_BLOCK.get());
        tag(LFLBlockTags.SALMON).add(LFLBlocks.SALMON_ROE_BLOCK.get());
        tag(LFLBlockTags.PUFFERFISH).add(LFLBlocks.PUFFERFISH_ROE_BLOCK.get());
        tag(LFLBlockTags.TROPICAL_FISH).add(LFLBlocks.TROPICAL_FISH_ROE_BLOCK.get());
        addOptional(LFLBlockTags.PIKE, LFLBlocks.PIKE_ROE_BLOCK.get());
        addOptional(LFLBlockTags.PERCH, LFLBlocks.PERCH_ROE_BLOCK.get());
        addOptional(LFLBlockTags.LIONFISH, LFLBlocks.LIONFISH_ROE_BLOCK.get());
        addOptional(LFLBlockTags.BASS, LFLBlocks.BASS_ROE_BLOCK.get());
        addOptional(LFLBlockTags.CATFISH, LFLBlocks.CATFISH_ROE_BLOCK.get());
        addOptional(LFLBlockTags.KOI, LFLBlocks.KOI_ROE_BLOCK.get());
    }

    private void addOptional(TagKey<Block> tagKey, Block block) {
        tag(tagKey).addOptional(ForgeRegistries.BLOCKS.getKey(block));
    }
}
