package com.uraneptus.letfishlove.common.entity;

import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
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
    private static boolean isInLove = false;
    private static boolean canFallInLove = false;
    @Nullable
    private static ServerPlayer loveCause;

    @Nullable
    public static ServerPlayer getLoveCause(Level level, AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
            if (cap.loveCause == null) {
                loveCause = null;
            } else {
                Player player = level.getPlayerByUUID(cap.loveCause);
                loveCause = player instanceof ServerPlayer ? (ServerPlayer)player : null;
            }
        });
        return loveCause;
    }

    public static boolean isInLove(AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
            isInLove = cap.inLove > 0;
        });
        return isInLove;
    }

    public static boolean canFallInLove(AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
            canFallInLove = cap.inLove <= 0;
        });
        return canFallInLove;
    }

    //TODO it seems like love is not reseted on both logical sides. Big problem
    public static void resetLove(AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
            cap.inLove = 0;
        });
    }

    public static void usePlayerItem(Player pPlayer, ItemStack pStack) {
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
    }

    public static void setInLove(@Nullable Player pPlayer, Level level, AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> {
            if (FishBreedingUtil.canFallInLove(fish)) {
                RandomSource random = fish.getRandom();
                cap.inLove = 600;
                if (pPlayer != null) {
                    cap.loveCause = pPlayer.getUUID();
                }
                level.broadcastEntityEvent(fish, (byte)18);
                for(int i = 0; i < 7; ++i) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    level.addParticle(ParticleTypes.HEART, fish.getRandomX(1.0D), fish.getRandomY() + 0.5D, fish.getRandomZ(1.0D), d0, d1, d2);
                }
            }
        });
    }

    public static boolean canMate(AbstractFish thisFish, AbstractFish pOtherFish) {
        if (pOtherFish == thisFish) {
            return false;
        } else if (pOtherFish.getClass() != thisFish.getClass()) {
            return false;
        } else {
            return isInLove(thisFish) && isInLove(pOtherFish);
        }
    }

    //TODO this will later handle laying roe etc. It's currently just
    public static void spawnFishFromBreeding(ServerLevel pLevel, AbstractFish fish, AbstractFish otherFish) {
        AbstractFish newFish = (AbstractFish) fish.getType().create(pLevel);

        if (newFish != null) {
            ServerPlayer serverplayer = getLoveCause(pLevel, fish);
            if (serverplayer == null && getLoveCause(pLevel, otherFish) != null) {
                serverplayer = getLoveCause(pLevel, otherFish);
            }

            if (serverplayer != null) {
                serverplayer.awardStat(Stats.ANIMALS_BRED);
            }

            resetLove(fish);
            resetLove(otherFish);
            newFish.moveTo(fish.getX(), fish.getY(), fish.getZ(), 0.0F, 0.0F);
            //TODO tropical fish will only spawn one type atm
            pLevel.addFreshEntity(newFish);
            pLevel.broadcastEntityEvent(fish, (byte)18);
            if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                pLevel.addFreshEntity(new ExperienceOrb(pLevel, fish.getX(), fish.getY(), fish.getZ(), fish.getRandom().nextInt(7) + 1));
            }
        }
    }
}
