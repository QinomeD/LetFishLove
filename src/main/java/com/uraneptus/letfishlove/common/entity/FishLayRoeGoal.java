package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.core.registry.LFLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class FishLayRoeGoal extends MoveToBlockGoal {
    private final AbstractFish fish;

    public FishLayRoeGoal(AbstractFish pMob, double pSpeedModifier, int pSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange, 3);
        this.fish = pMob;
    }

    @Override
    public boolean canUse() {
        return FishBreedingUtil.getFishCap(fish).isPregnant() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return FishBreedingUtil.getFishCap(fish).isPregnant() && super.canContinueToUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getFluidState(pPos).is(Fluids.WATER) && pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public void tick() {
        Level level = fish.getLevel();
        if (this.isReachedTarget()) {
            ResourceLocation registryName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType());
            TagKey<Block> blockTag = TagKey.create(Registry.BLOCK_REGISTRY, LetFishLoveMod.modPrefix("fish_roe/" + registryName.getPath()));
            System.out.println(blockTag.location());
            List<Block> roeBlocks = ForgeRegistries.BLOCKS.tags().getTag(blockTag).stream().toList();
            System.out.println(roeBlocks);
            System.out.println(roeBlocks.size());
            if (!roeBlocks.isEmpty()) {

                int entry = 0;
                if (roeBlocks.size() > 1) {
                    entry = level.getRandom().nextIntBetweenInclusive(0, roeBlocks.size() - 1);
                }

                level.setBlockAndUpdate(getMoveToTarget(), roeBlocks.get(entry).defaultBlockState());
            }
            FishBreedingUtil.getFishCap(fish).setPregnant(false, true);
        }
        super.tick();
    }
}
