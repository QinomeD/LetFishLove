package com.uraneptus.letfishlove.core.registry;

import com.starfish_studios.naturalist.registry.NaturalistEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.upgrade_aquatic.core.registry.UAEntityTypes;
import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.RoeBlock;
import com.uraneptus.letfishlove.core.other.LFLProperties;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LFLBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LetFishLoveMod.MOD_ID);

    public static final RegistryObject<Block> COD_ROE_BLOCK = BLOCKS.register("cod_roe_block", () -> new RoeBlock(() -> EntityType.COD, LFLProperties.roeBlockProperties()));
    public static final RegistryObject<Block> PUFFERFISH_ROE_BLOCK = BLOCKS.register("pufferfish_roe_block", () -> new RoeBlock(() -> EntityType.PUFFERFISH, LFLProperties.roeBlockProperties()));
    public static final RegistryObject<Block> SALMON_ROE_BLOCK = BLOCKS.register("salmon_roe_block", () -> new RoeBlock(() -> EntityType.SALMON, LFLProperties.roeBlockProperties()));
    public static final RegistryObject<Block> TROPICAL_FISH_ROE_BLOCK = BLOCKS.register("tropical_fish_roe_block", () -> new RoeBlock(() -> EntityType.TROPICAL_FISH, LFLProperties.roeBlockProperties()));

    public static final RegistryObject<Block> PIKE_ROE_BLOCK = BLOCKS.register("pike_roe_block",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeBlock(UAEntityTypes.PIKE::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));

    public static final RegistryObject<Block> PERCH_ROE_BLOCK = BLOCKS.register("perch_roe_block",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeBlock(UAEntityTypes.PERCH::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));

    public static final RegistryObject<Block> LIONFISH_ROE_BLOCK = BLOCKS.register("lionfish_roe_block",
            () -> ModList.get().isLoaded("upgrade_aquatic")
                    ? new RoeBlock(UAEntityTypes.LIONFISH::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));


    public static final RegistryObject<Block> BASS_ROE_BLOCK = BLOCKS.register("bass_roe_block",
            () -> ModList.get().isLoaded("naturalist")
                    ? new RoeBlock(NaturalistEntityTypes.BASS::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));

    public static final RegistryObject<Block> CATFISH_ROE_BLOCK = BLOCKS.register("catfish_roe_block",
            () -> ModList.get().isLoaded("naturalist")
                    ? new RoeBlock(NaturalistEntityTypes.CATFISH::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));


    public static final RegistryObject<Block> KOI_ROE_BLOCK = BLOCKS.register("koi_roe_block",
            () -> ModList.get().isLoaded("environmental")
                    ? new RoeBlock(EnvironmentalEntityTypes.KOI::get, LFLProperties.roeBlockProperties())
                    : new Block(LFLProperties.roeBlockProperties()));
}