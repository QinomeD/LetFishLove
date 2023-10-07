package com.uraneptus.letfishlove.core.registry;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.items.RoeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LFLItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LetFishLoveMod.MOD_ID);

    public static final RegistryObject<Item> COD_ROE = ITEMS.register("cod_roe", () -> new RoeItem(LFLBlocks.COD_ROE_BLOCK.get()));
    public static final RegistryObject<Item> PUFFERFISH_ROE = ITEMS.register("pufferfish_roe", () -> new RoeItem(LFLBlocks.PUFFERFISH_ROE_BLOCK.get()));
    public static final RegistryObject<Item> SALMON_ROE = ITEMS.register("salmon_roe", () -> new RoeItem(LFLBlocks.SALMON_ROE_BLOCK.get()));
    public static final RegistryObject<Item> TROPICAL_FISH_ROE = ITEMS.register("tropical_fish_roe", () -> new RoeItem(LFLBlocks.TROPICAL_FISH_ROE_BLOCK.get()));

}
