package com.uraneptus.letfishlove.data.client;


import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.registry.LFLItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.uraneptus.letfishlove.data.LFLDatagenUtil.*;

public class LFLItemModelProvider extends ItemModelProvider {

    public LFLItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LetFishLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(LFLItems.COD_ROE);
        basicItem(LFLItems.PUFFERFISH_ROE);
        basicItem(LFLItems.SALMON_ROE);
        basicItem(LFLItems.TROPICAL_FISH_ROE);
    }

    private void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }

    private void basicBlockItem(Supplier<? extends Block> blockForItem) {
        withExistingParent(name(blockForItem.get()), modBlockLocation(name(blockForItem.get())));
    }

    private void generatedBlockItemWithItemTexture(Supplier<? extends Block> block) {
        withExistingParent(name(block.get()), GENERATED).texture(LAYER0, modItemLocation(name(block.get())));
    }
}
