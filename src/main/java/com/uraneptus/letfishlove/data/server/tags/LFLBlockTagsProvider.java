package com.uraneptus.letfishlove.data.server.tags;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.RoeBlock;
import com.uraneptus.letfishlove.core.other.LFLBlockTags;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LFLBlockTagsProvider extends BlockTagsProvider {

    public LFLBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider pProvider) {
        RoeBlock.getAllBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block));

        tag(LFLBlockTags.COD).add(LFLBlocks.COD_ROE_BLOCK.get());
        tag(LFLBlockTags.SALMON).add(LFLBlocks.SALMON_ROE_BLOCK.get());
        tag(LFLBlockTags.PUFFERFISH).add(LFLBlocks.PUFFERFISH_ROE_BLOCK.get());
        tag(LFLBlockTags.TROPICAL_FISH).add(LFLBlocks.TROPICAL_FISH_ROE_BLOCK.get());

    }

}
