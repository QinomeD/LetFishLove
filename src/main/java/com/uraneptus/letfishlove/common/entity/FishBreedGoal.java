package com.uraneptus.letfishlove.common.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class FishBreedGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    protected final WaterAnimal fish;
    private final Class<? extends WaterAnimal> partnerClass;
    protected final Level level;
    @Nullable
    protected WaterAnimal partner;
    private int loveTime;
    private final double speedModifier;

    public FishBreedGoal(WaterAnimal fish, double pSpeedModifier) {
        this(fish, pSpeedModifier, fish.getClass());
    }

    public FishBreedGoal(WaterAnimal fish, double pSpeedModifier, Class<? extends WaterAnimal> pPartnerClass) {
        this.fish = fish;
        this.level = fish.level;
        this.partnerClass = pPartnerClass;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (!FishBreedingUtil.getFishCap(fish).isInLove() || FishBreedingUtil.getFishCap(fish).isPregnant()) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null;
        }
    }

    public boolean canContinueToUse() {
        return this.partner != null && this.partner.isAlive() && FishBreedingUtil.getFishCap(partner).isInLove() && this.loveTime < 100;
    }

    public void stop() {
        this.partner = null;
        this.loveTime = 0;
    }

    public void tick() {
        if (partner == null) {
            return;
        }
        this.fish.getLookControl().setLookAt(this.partner, 10.0F, (float)this.fish.getMaxHeadXRot());
        this.fish.getNavigation().moveTo(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= this.adjustedTickDelay(100) && this.fish.distanceToSqr(this.partner) < 9.0D) {
            this.breed();
        }
    }

    @Nullable
    private WaterAnimal getFreePartner() {
        List<? extends WaterAnimal> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.fish, this.fish.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        WaterAnimal partner = null;

        for(WaterAnimal otherFish : list) {
            if (FishBreedingUtil.canMate(this.fish, otherFish) && this.fish.distanceToSqr(otherFish) < d0) {
                partner = otherFish;
                d0 = this.fish.distanceToSqr(otherFish);
            }
        }

        return partner;
    }

    protected void breed() {
        FishBreedingUtil.spawnFishFromBreeding((ServerLevel)this.level, this.fish, this.partner);
    }
}
