package com.uraneptus.letfishlove.common.items;

import com.uraneptus.letfishlove.core.other.LFLProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class RoeItem extends PlaceOnWaterBlockItem {

    public RoeItem(Block pBlock) {
        super(pBlock, LFLProperties.roeItemProperties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockState blockState = level.getBlockState(hitResult.getBlockPos());
        ItemStack itemstack = player.getItemInHand(hand);
        if (!(blockState.getFluidState().is(Fluids.WATER) && level.getBlockState(hitResult.getBlockPos().above()).isAir())) {
            if (player.canEat(itemstack.getFoodProperties(player).canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return super.use(level, player, hand);
        }
    }

}
