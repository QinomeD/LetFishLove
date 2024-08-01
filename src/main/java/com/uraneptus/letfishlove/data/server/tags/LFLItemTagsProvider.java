package com.uraneptus.letfishlove.data.server.tags;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.other.LFLItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class LFLItemTagsProvider extends ItemTagsProvider {

    public LFLItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    protected void addTags() {
        tag(LFLItemTags.COD).add(Items.BEETROOT);
        tag(LFLItemTags.SALMON).add(Items.SWEET_BERRIES);
        tag(LFLItemTags.PUFFERFISH).add(Items.GOLDEN_CARROT);
        tag(LFLItemTags.TROPICAL_FISH).add(Items.MELON_SLICE);
        tag(LFLItemTags.PIKE).add(Items.SALMON);
        tag(LFLItemTags.PERCH).add(Items.COD);
        tag(LFLItemTags.LIONFISH).add(Items.TROPICAL_FISH);
        tag(LFLItemTags.BASS).add(Items.POTATO);
        tag(LFLItemTags.CATFISH).add(Items.SEAGRASS);
    }
}
