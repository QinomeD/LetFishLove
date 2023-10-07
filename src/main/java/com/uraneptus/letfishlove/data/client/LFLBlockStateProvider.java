package com.uraneptus.letfishlove.data.client;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.RoeBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.uraneptus.letfishlove.data.LFLDatagenUtil.*;

public class LFLBlockStateProvider extends BlockStateProvider {

    public LFLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LetFishLoveMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        RoeBlock.getAllBlocks().forEach((this::roeBlock));
    }

    private void roeBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile modelFile = models().withExistingParent(name(block), vanillaBlockLocation(FROGSPAWN_PARENT))
                    .texture("particle", modBlockLocation(name(block))).texture("texture", modBlockLocation(name(block))).renderType("translucent");
            return ConfiguredModel.builder().modelFile(modelFile).build();
        });
    }


}
