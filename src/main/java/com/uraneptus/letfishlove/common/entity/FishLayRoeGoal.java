package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.TropicalFishRoeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;
import java.util.List;

public class FishLayRoeGoal extends MoveToBlockGoal {
    private final AbstractFish fish;

    public FishLayRoeGoal(AbstractFish fish) {
        super(fish, 0.8F, 10, 5);
        this.fish = fish;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return FishBreedingUtil.getFishCap(fish).isPregnant() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.fish.getNavigation().isDone() && FishBreedingUtil.getFishCap(fish).isPregnant() && super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 0.0D;
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.above()).isAir() && pLevel.getBlockState(pPos).getFluidState().is(Fluids.WATER);
    }

    @Override
    public void stop() {
        Level level = this.fish.level();
        BlockPos fishPos = this.getMoveToTarget();
        String fishTypeName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType()).getPath();
        TagKey<Block> blockTag = TagKey.create(Registries.BLOCK, LetFishLoveMod.modPrefix("fish_roe/" + fishTypeName));
        List<Block> roeBlocks = ForgeRegistries.BLOCKS.tags().getTag(blockTag).stream().toList();
        if (!roeBlocks.isEmpty()) {
            int entry = 0;
            if (roeBlocks.size() > 1) {
                entry = level.getRandom().nextIntBetweenInclusive(0, roeBlocks.size() - 1);
            }

            Block roe = roeBlocks.get(entry);
            if (fish instanceof TropicalFish tropicalFish && roe instanceof TropicalFishRoeBlock roeBlock) {
                roeBlock.setFishVariant(tropicalFish.getVariant());
            }
            level.setBlockAndUpdate(fishPos, roe.defaultBlockState());
        }
        FishBreedingUtil.getFishCap(fish).setPregnant(false, true);
    }
}