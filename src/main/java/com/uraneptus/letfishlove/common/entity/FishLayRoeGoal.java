package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.blocks.TropicalFishRoeBlock;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCapAttacher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class FishLayRoeGoal extends Goal {
    private final AbstractFish fish;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    public FishLayRoeGoal(AbstractFish fish) {
        this.fish = fish;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        LazyOptional<AbstractFishCap> fishOptional = AbstractFishCapAttacher.getAbstractFishCapability(this.fish).cast();
        if (!fishOptional.isPresent() || fishOptional.resolve().isEmpty()) {
            return false;
        } else {
            return fishOptional.resolve().get().isPregnant() && this.setWantedPos();
        }
    }

    protected boolean setWantedPos() {
        Vec3 vec3 = this.getPosition();
        if (vec3 == null) {
            return false;
        } else {
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        LazyOptional<AbstractFishCap> fishOptional = AbstractFishCapAttacher.getAbstractFishCapability(this.fish).cast();
        return !this.fish.getNavigation().isDone() && (fishOptional.isPresent() || !fishOptional.resolve().isEmpty()) && fishOptional.resolve().get().isPregnant();
    }

    @Override
    public void start() {
        this.fish.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1.0D);
    }

    @Override
    public void stop() {
        Level level = this.fish.getLevel();
        String fishTypeName = ForgeRegistries.ENTITY_TYPES.getKey(fish.getType()).getPath();
        TagKey<Block> blockTag = TagKey.create(Registry.BLOCK_REGISTRY, LetFishLoveMod.modPrefix("fish_roe/" + fishTypeName));
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
            level.setBlockAndUpdate(new BlockPos(wantedX, wantedY, wantedZ).above(), roe.defaultBlockState());
        }
        FishBreedingUtil.getFishCap(fish).setPregnant(false, true);
    }

    @Nullable
    protected Vec3 getPosition() {
        Level level = this.fish.getLevel();

        for(BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.fish.getX() - 5.0D), Mth.floor(this.fish.getY() - 5.0D), Mth.floor(this.fish.getZ() - 5.0D), Mth.floor(this.fish.getX() + 5.0D), this.fish.getBlockY(), Mth.floor(this.fish.getZ() + 5.0D))) {
            if (level.getFluidState(blockpos1).is(Fluids.WATER) && level.getBlockState(blockpos1.above()).isAir() /*&& level.getBlockState(blockpos1.relative(fish.getMotionDirection())).isCollisionShapeFullBlock(level, blockpos1)*/) {
                return Vec3.atCenterOf(blockpos1);
            }
        }
        return null;
    }

}
