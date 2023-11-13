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

    public static AbstractFishCap getFishCap(AbstractFish fish) {
        LazyOptional<AbstractFishCap> fishOptional = AbstractFishCapAttacher.getAbstractFishCapability(fish).cast();
        if (fishOptional.isPresent() || !fishOptional.resolve().isEmpty()) {
            return fishOptional.resolve().get();
        }
        return new AbstractFishCap(fish);
    }

    public static boolean canMate(AbstractFish thisFish, AbstractFish pOtherFish) {
        if (pOtherFish == thisFish) {
            return false;
        } else if (pOtherFish.getClass() != thisFish.getClass()) {
            return false;
        } else {
            return getFishCap(thisFish).isInLove() && getFishCap(pOtherFish).isInLove();
        }
    }

    public static void spawnFishFromBreeding(ServerLevel pLevel, AbstractFish thisFish, AbstractFish otherFish) {
        AbstractFishCap thisFishCap = getFishCap(thisFish);
        AbstractFishCap otherFishCap = getFishCap(otherFish);

        ServerPlayer serverplayer = thisFishCap.getLoveCause(pLevel);
        if (serverplayer == null && otherFishCap.getLoveCause(pLevel) != null) {
            serverplayer = otherFishCap.getLoveCause(pLevel);
        }

        if (serverplayer != null) {
            //Usually a trigger would be here
            //TODO figure out how to trigger this
            serverplayer.awardStat(Stats.ANIMALS_BRED);
        }

        thisFishCap.resetLove();
        otherFishCap.resetLove();
        thisFishCap.setCanLoveCooldown(6000, true);
        otherFishCap.setCanLoveCooldown(6000, true);
        thisFishCap.setPregnant(true, true);
        pLevel.broadcastEntityEvent(thisFish, (byte)18);
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            pLevel.addFreshEntity(new ExperienceOrb(pLevel, thisFish.getX(), thisFish.getY(), thisFish.getZ(), thisFish.getRandom().nextInt(7) + 1));
        }
    }
}
