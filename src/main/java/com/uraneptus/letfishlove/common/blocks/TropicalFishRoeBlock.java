package com.uraneptus.letfishlove.common.blocks;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.TropicalFish;

import java.util.function.Supplier;

public class TropicalFishRoeBlock extends RoeBlock {
    private int fishVariant = 0;

    public TropicalFishRoeBlock(Supplier<EntityType<?>> fish, Properties properties) {
        super(fish, properties);
    }

    @Override
    protected void spawnFish(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pRandom.nextInt(calculateHatchAmount(pLevel).getMinValue(), calculateHatchAmount(pLevel).getMaxValue());

        for(int j = 1; j <= i; ++j) {
            if (getFish() != null && getFish().create(pLevel) instanceof TropicalFish tropicalFish) {
                double d0 = (double)pPos.getX() + this.getRandomPositionOffset(pRandom);
                double d1 = (double)pPos.getZ() + this.getRandomPositionOffset(pRandom);
                int k = pRandom.nextInt(1, 361);
                tropicalFish.moveTo(d0, (double)pPos.getY() - 0.5D, d1, (float)k, 0.0F);
                tropicalFish.setPersistenceRequired();
                if (this.getFishVariant() == 0) {
                    setRandomVariant(tropicalFish, pRandom);
                } else {
                    tropicalFish.setPackedVariant(this.getFishVariant());
                }

                pLevel.addFreshEntity(tropicalFish);
            }
        }
    }

    public void setRandomVariant(TropicalFish tropicalFish, RandomSource randomSource) {
        TropicalFish.Variant randomVariant = Util.getRandom(TropicalFish.COMMON_VARIANTS, randomSource);
        int variantId = randomVariant.getPackedId();
        tropicalFish.setPackedVariant(TropicalFish.packVariant(TropicalFish.getPattern(variantId), TropicalFish.getBaseColor(variantId), TropicalFish.getPatternColor(variantId)));
    }

    public int getFishVariant() {
        return this.fishVariant;
    }

    public void setFishVariant(int variant) {
        this.fishVariant = variant;
    }
}
