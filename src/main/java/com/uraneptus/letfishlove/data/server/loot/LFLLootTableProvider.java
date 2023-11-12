package com.uraneptus.letfishlove.data.server.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class LFLLootTableProvider extends LootTableProvider {
    public LFLLootTableProvider(PackOutput pOutput) {
        super(pOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(LFLBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}
