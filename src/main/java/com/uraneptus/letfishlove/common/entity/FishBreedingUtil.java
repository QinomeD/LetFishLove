package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCapAttacher;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class FishBreedingUtil {

    public static void usePlayerItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    public static boolean canMate(AbstractFish thisFish, AbstractFish pOtherFish) {
        LazyOptional<AbstractFishCap> thisFishOptional = AbstractFishCapAttacher.getAbstractFishCapability(thisFish).cast();
        LazyOptional<AbstractFishCap> otherFishOptional = AbstractFishCapAttacher.getAbstractFishCapability(pOtherFish).cast();

        if ((!thisFishOptional.isPresent() || thisFishOptional.resolve().isEmpty()) && (!otherFishOptional.isPresent() || otherFishOptional.resolve().isEmpty())) {
            return false;
        }
        if (pOtherFish == thisFish) {
            return false;
        } else if (pOtherFish.getClass() != thisFish.getClass()) {
            return false;
        } else {
            AbstractFishCap thisFishCap = thisFishOptional.resolve().get();
            AbstractFishCap otherFishCap = otherFishOptional.resolve().get();
            return thisFishCap.isInLove() && otherFishCap.isInLove();
        }
    }

    //TODO this will later handle laying roe etc. It's currently just
    public static void spawnFishFromBreeding(ServerLevel pLevel, AbstractFish thisFish, AbstractFish otherFish) {
        LazyOptional<AbstractFishCap> thisFishOptional = AbstractFishCapAttacher.getAbstractFishCapability(thisFish).cast();
        LazyOptional<AbstractFishCap> otherFishOptional = AbstractFishCapAttacher.getAbstractFishCapability(otherFish).cast();
        if ((!thisFishOptional.isPresent() || thisFishOptional.resolve().isEmpty()) && (!otherFishOptional.isPresent() || otherFishOptional.resolve().isEmpty())) {
            return;
        }
        AbstractFishCap thisFishCap = thisFishOptional.resolve().get();
        AbstractFishCap otherFishCap = otherFishOptional.resolve().get();
        AbstractFish newFish = (AbstractFish) thisFish.getType().create(pLevel);

        if (newFish != null) {
            ServerPlayer serverplayer = thisFishCap.getLoveCause(pLevel);
            if (serverplayer == null && otherFishCap.getLoveCause(pLevel) != null) {
                serverplayer = otherFishCap.getLoveCause(pLevel);
            }

            if (serverplayer != null) {
                serverplayer.awardStat(Stats.ANIMALS_BRED);
            }
            thisFishCap.resetLove();
            otherFishCap.resetLove();
            newFish.moveTo(thisFish.getX(), thisFish.getY(), thisFish.getZ(), 0.0F, 0.0F);
            //TODO randomize tropical fish look
            pLevel.addFreshEntity(newFish);
            pLevel.broadcastEntityEvent(thisFish, (byte)18);
            if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                pLevel.addFreshEntity(new ExperienceOrb(pLevel, thisFish.getX(), thisFish.getY(), thisFish.getZ(), thisFish.getRandom().nextInt(7) + 1));
            }
        }
    }
}
