package com.uraneptus.letfishlove.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.TropicalFish;

public class TropicalFishRoeBlock extends RoeBlock {
    private TropicalFish.Pattern fishVariant;

    public TropicalFishRoeBlock(EntityType<?> fish, Properties properties) {
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
                tropicalFish.setVariant(this.getFishVariant());
                pLevel.addFreshEntity(tropicalFish);
            }
        }
    }

    public TropicalFish.Pattern getFishVariant() {
        return this.fishVariant;
    }

    public void setFishVariant(TropicalFish.Pattern variant) {
        this.fishVariant = variant;
    }
}
