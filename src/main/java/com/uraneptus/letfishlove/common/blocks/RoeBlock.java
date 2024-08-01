package com.uraneptus.letfishlove.common.blocks;

import com.teamabnormals.upgrade_aquatic.common.entity.animal.Pike;
import com.teamabnormals.upgrade_aquatic.common.entity.animal.PikeType;
import com.uraneptus.letfishlove.common.RoeHatchDataReloadListener;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RoeBlock extends Block {
    private Supplier<EntityType<?>> fish;
    protected boolean fromBreeding = false;
    private Entity parentEntity;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);

    public RoeBlock(Supplier<EntityType<?>> fish, Properties properties) {
        super(properties);
        this.fish = fish;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return mayPlaceOn(pLevel, pPos.below());
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, getHatchDelay(pLevel.getRandom()));
    }

    protected int getHatchDelay(RandomSource pRandom) {
        if (!FMLEnvironment.production) {
            return pRandom.nextInt(36, 120);
        }
        RoeHatchDataReloadListener.RoeData roeData = RoeHatchDataReloadListener.ROE_HATCH_DATA_MAP.get(ForgeRegistries.BLOCKS.getKey(this.asBlock()));
        return pRandom.nextInt(roeData.minHatchDuration(), roeData.maxHatchDuration());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!this.canSurvive(pState, pLevel, pPos)) {
            this.destroyBlock(pLevel, pPos);
        } else {
            this.hatch(pLevel, pPos, pRandom);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.destroyBlock(pLevel, pPos);
        }
    }

    protected static boolean mayPlaceOn(BlockGetter pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        FluidState fluidstate1 = pLevel.getFluidState(pPos.above());
        return fluidstate.getType() == Fluids.WATER && fluidstate1.getType() == Fluids.EMPTY;
    }

    protected void hatch(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.destroyBlock(pLevel, pPos);
        pLevel.playSound(null, pPos, SoundEvents.FROGSPAWN_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.spawnFish(pLevel, pPos, pRandom);
    }

    private void destroyBlock(Level pLevel, BlockPos pPos) {
        pLevel.destroyBlock(pPos, false);
    }

    protected void spawnFish(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        RoeHatchDataReloadListener.RoeData roeData = RoeHatchDataReloadListener.ROE_HATCH_DATA_MAP.get(ForgeRegistries.BLOCKS.getKey(this.asBlock()));
        int i = pRandom.nextInt(roeData.minHatchAmount(), roeData.maxHatchAmount());

        for(int j = 1; j <= i; ++j) {
            if (createEntity(pLevel) instanceof WaterAnimal waterAnimal) {
                double d0 = (double)pPos.getX() + this.getRandomPositionOffset(pRandom);
                double d1 = (double)pPos.getZ() + this.getRandomPositionOffset(pRandom);
                int k = pRandom.nextInt(1, 361);
                waterAnimal.moveTo(d0, (double)pPos.getY() - 0.5D, d1, (float)k, 0.0F);
                waterAnimal.setPersistenceRequired();
                handleVariantFish(waterAnimal, pLevel, pPos, pRandom);
                pLevel.addFreshEntity(waterAnimal);
            }
        }
    }

    protected void handleVariantFish(WaterAnimal waterAnimal, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (waterAnimal instanceof Pike pike) {
            if (fromBreeding && this.getParentEntity() instanceof Pike parentFish) {
                pike.setPikeType(parentFish.getPikeType());
            } else {
                pike.setPikeType(PikeType.getRandom(pLevel.getRandom(), pLevel.getBiome(pPos), false));
            }
            return;
        }

        if (waterAnimal instanceof TropicalFish newtropicalFish) {
            if (fromBreeding && this.getParentEntity() instanceof TropicalFish parentFish) {
                newtropicalFish.setVariant(parentFish.getVariant());
            } else {
                newtropicalFish.setVariant(Util.getRandom(TropicalFish.COMMON_VARIANTS, pRandom));
            }
        }
    }

    protected Entity createEntity(Level pLevel) {
        if (this.getFish() != null) {
            return this.getFish().create(pLevel);
        } else {
            //Should never reach this
            throw new NullPointerException("Failed to create Fish entity because the supplied entity type is null");
        }
    }

    protected double getRandomPositionOffset(RandomSource pRandom) {
        double d0 = getFish().getWidth() / 2.0F;
        return Mth.clamp(pRandom.nextDouble(), d0, 1.0D - d0);
    }

    public EntityType<?> getFish() {
        return fish.get();
    }

    public void setFish(Supplier<EntityType<?>> fish) {
        this.fish = fish;
    }

    public Entity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(Entity parentEntity) {
        this.parentEntity = parentEntity;
        this.fromBreeding = true;
    }
}
