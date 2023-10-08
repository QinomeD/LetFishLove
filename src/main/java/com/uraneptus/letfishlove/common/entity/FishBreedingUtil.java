package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class FishBreedingUtil {

    public void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    public void setInLove(@Nullable Player pPlayer, Level level, AbstractFish entity) {
        AbstractFishCap.getCapOptional(entity).ifPresent(cap -> {
            cap.inLove = 600;
            if (pPlayer != null) {
                cap.loveCause = pPlayer.getUUID();
            }
            level.broadcastEntityEvent(entity, (byte)18);
        });
    }

    public void aiStepInLove(AbstractFish entity, Level level) {
        AbstractFishCap.getCapOptional(entity).ifPresent(cap -> {
            RandomSource random = entity.getRandom();
            if (cap.isInLove()) {
                --cap.inLove;
                if (cap.inLove % 10 == 0) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    level.addParticle(ParticleTypes.HEART, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                }
            }
        });
    }

    public boolean canMate(AbstractFish thisFish, AbstractFish pOtherFish) {
        boolean thisFishInLove = false;
        boolean otherFishInLove = false;
        if (pOtherFish == thisFish) {
            return false;
        } else if (pOtherFish.getClass() != thisFish.getClass()) {
            return false;
        } else {
            LazyOptional<AbstractFishCap> capThisFish = AbstractFishCap.getCapOptional(thisFish);
            LazyOptional<AbstractFishCap> capOtherFish = AbstractFishCap.getCapOptional(thisFish);
            if (capThisFish.isPresent() && capOtherFish.isPresent()) {
                thisFishInLove = capThisFish.orElse(new AbstractFishCap()).isInLove();
                otherFishInLove = capOtherFish.orElse(new AbstractFishCap()).isInLove();
            }
            return thisFishInLove && otherFishInLove;
        }
    }

    public void spawnFishFromBreeding(ServerLevel pLevel, AbstractFish fish, AbstractFish otherFish) {
        //AbstractFish ageablemob = this.getBreedOffspring(pLevel, pMate);
        AbstractFish newFish = (AbstractFish) fish.getType().create(pLevel);

        if (newFish != null) {
            AbstractFishCap fishCap = AbstractFishCap.getCapOptional(fish).orElse(new AbstractFishCap());
            AbstractFishCap otherFishCap = AbstractFishCap.getCapOptional(otherFish).orElse(new AbstractFishCap());
            ServerPlayer serverplayer = fishCap.getLoveCause(pLevel);
            if (serverplayer == null && otherFishCap.getLoveCause(pLevel) != null) {
                serverplayer = otherFishCap.getLoveCause(pLevel);
            }

            if (serverplayer != null) {
                serverplayer.awardStat(Stats.ANIMALS_BRED);

            }


            fishCap.resetLove();
            otherFishCap.resetLove();
            newFish.moveTo(fish.getX(), fish.getY(), fish.getZ(), 0.0F, 0.0F);
            pLevel.addFreshEntity(newFish);
            pLevel.broadcastEntityEvent(fish, (byte)18);
            if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                pLevel.addFreshEntity(new ExperienceOrb(pLevel, fish.getX(), fish.getY(), fish.getZ(), fish.getRandom().nextInt(7) + 1));
            }

        }
    }
}
