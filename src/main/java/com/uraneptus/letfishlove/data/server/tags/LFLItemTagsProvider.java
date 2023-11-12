package com.uraneptus.letfishlove.data.server.tags;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.other.LFLItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LFLItemTagsProvider extends ItemTagsProvider {

    public LFLItemTagsProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> pProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, pProvider, blockProvider, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(LFLItemTags.COD).add(Items.BEETROOT);
        tag(LFLItemTags.SALMON).add(Items.SWEET_BERRIES);
        tag(LFLItemTags.PUFFERFISH).add(Items.GOLDEN_CARROT);
        tag(LFLItemTags.TROPICAL_FISH).add(Items.MELON_SLICE);
    }

}
