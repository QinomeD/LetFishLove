package com.uraneptus.letfishlove.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LFLLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> lootTables = ImmutableList.of(Pair.of(LFLBlockLoot::new, LootContextParamSets.BLOCK));

    public LFLLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.lootTables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    }

    private static class LFLBlockLoot extends BlockLoot {

        @Override
        protected void addTables() {
            LFLBlocks.BLOCKS.getEntries().forEach(block -> dropSelf(block.get()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return LFLBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
