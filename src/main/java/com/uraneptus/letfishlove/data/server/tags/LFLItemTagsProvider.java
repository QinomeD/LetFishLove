package com.uraneptus.letfishlove.data.server.tags;

import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.other.LFLItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
        addOptional(LFLItemTags.PIKE, Items.SALMON);
        addOptional(LFLItemTags.PERCH, Items.COD);
        addOptional(LFLItemTags.LIONFISH, Items.TROPICAL_FISH);
        addOptional(LFLItemTags.BASS, Items.POTATO);
        addOptional(LFLItemTags.CATFISH, Items.SEAGRASS);
        addOptional(LFLItemTags.KOI, EnvironmentalItems.CHERRIES.get());
    }

    private void addOptional(TagKey<Item> tagKey, Item item) {
        tag(tagKey).addOptional(ForgeRegistries.ITEMS.getKey(item));
    }
}
