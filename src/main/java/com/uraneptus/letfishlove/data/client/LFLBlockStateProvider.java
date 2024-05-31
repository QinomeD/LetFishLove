package com.uraneptus.letfishlove.data.client;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.uraneptus.letfishlove.data.LFLDatagenUtil.*;

public class LFLBlockStateProvider extends BlockStateProvider {

    public LFLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LetFishLoveMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        LFLBlocks.BLOCKS.getEntries().forEach(this::roeBlock);
    }

    private void roeBlock(Supplier<Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            ModelFile modelFile = models().withExistingParent(name(block.get()), vanillaBlockLocation(FROGSPAWN_PARENT))
                    .texture("particle", modBlockLocation(name(block.get()))).texture("texture", modBlockLocation(name(block.get()))).renderType("translucent");
            return ConfiguredModel.builder().modelFile(modelFile).build();
        });
    }


}
