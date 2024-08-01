package com.uraneptus.letfishlove.core.registry;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.items.RoeItem;
import com.uraneptus.letfishlove.core.other.LFLProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LFLItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LetFishLoveMod.MOD_ID);

    public static final RegistryObject<Item> COD_ROE = ITEMS.register("cod_roe", () -> new RoeItem(LFLBlocks.COD_ROE_BLOCK));
    public static final RegistryObject<Item> PUFFERFISH_ROE = ITEMS.register("pufferfish_roe", () -> new RoeItem(LFLBlocks.PUFFERFISH_ROE_BLOCK));
    public static final RegistryObject<Item> SALMON_ROE = ITEMS.register("salmon_roe", () -> new RoeItem(LFLBlocks.SALMON_ROE_BLOCK));
    public static final RegistryObject<Item> TROPICAL_FISH_ROE = ITEMS.register("tropical_fish_roe", () -> new RoeItem(LFLBlocks.TROPICAL_FISH_ROE_BLOCK));

    public static final RegistryObject<Item> PIKE_ROE = ITEMS.register("pike_roe",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeItem(LFLBlocks.PIKE_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));
    public static final RegistryObject<Item> PERCH_ROE = ITEMS.register("perch_roe",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeItem(LFLBlocks.PERCH_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));
    public static final RegistryObject<Item> LIONFISH_ROE = ITEMS.register("lionfish_roe",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeItem(LFLBlocks.LIONFISH_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));

    public static final RegistryObject<Item> BASS_ROE = ITEMS.register("bass_roe",
            () -> ModList.get().isLoaded("naturalist")
                    ? new RoeItem(LFLBlocks.BASS_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));
    public static final RegistryObject<Item> CATFISH_ROE = ITEMS.register("catfish_roe",
            () -> ModList.get().isLoaded("naturalist")
                    ? new RoeItem(LFLBlocks.CATFISH_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));

    public static final RegistryObject<Item> KOI_ROE = ITEMS.register("koi_roe",
            () -> ModList.get().isLoaded("environmental")
                    ? new RoeItem(LFLBlocks.KOI_ROE_BLOCK)
                    : new Item(LFLProperties.roeItemProperties()));
}
