package com.uraneptus.letfishlove.data.client;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LFLLangProvider extends LanguageProvider {
    public LFLLangProvider(PackOutput packOutput) {
        super(packOutput, LetFishLoveMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlock(LFLBlocks.COD_ROE_BLOCK, "Cod Roe");
        addBlock(LFLBlocks.PUFFERFISH_ROE_BLOCK, "Pufferfish Roe");
        addBlock(LFLBlocks.SALMON_ROE_BLOCK, "Salmon Roe");
        addBlock(LFLBlocks.TROPICAL_FISH_ROE_BLOCK, "Tropical Fish Roe");


    }
}
