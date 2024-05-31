package com.uraneptus.letfishlove.common.capabilities;

import dev._100media.capabilitysyncer.core.LivingEntityCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.UUID;

public class FishBreedingCap extends LivingEntityCapability {
    public int inLove;
    @Nullable
    public UUID loveCause;
    public boolean isPregnant;
    public int canLoveCooldown;

    public FishBreedingCap(WaterAnimal entity) {
        super(entity);
    }

    public int getInLoveInt() {
        return inLove;
    }

    public void setInLoveInt(int inLove, boolean sync) {
        this.inLove = inLove;
        if (sync) {
            this.updateTracking();
        }
    }

    @Nullable
    public UUID getLoveCauseUUID() {
        return loveCause;
    }

    public void setLoveCauseUUID(@Nullable UUID loveCause, boolean sync) {
        this.loveCause = loveCause;
        if (sync) {
            this.updateTracking();
        }
    }

    public boolean canFallInLove() {
        return this.getInLoveInt() <= 0 && !this.isPregnant() && this.getCanLoveCooldown() == 0;
    }

    public void setInLove(WaterAnimal entity, @Nullable Player pPlayer, Level level) {
        RandomSource random = level.getRandom();
        this.setInLoveInt(600, true);
        if (pPlayer != null) {
            this.setLoveCauseUUID(pPlayer.getUUID(), true);
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            level.addParticle(ParticleTypes.HEART, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Nullable
    public ServerPlayer getLoveCause(Level level) {
        if (this.getLoveCauseUUID() == null) {
            return null;
        } else {
            Player player = level.getPlayerByUUID(this.getLoveCauseUUID());
            return player instanceof ServerPlayer ? (ServerPlayer)player : null;
        }
    }

    public boolean isInLove() {
        return this.getInLoveInt() > 0;
    }

    public void resetLove() {
        this.setInLoveInt(0, true);
    }

    public boolean isPregnant() {
        return this.isPregnant;
    }

    public void setPregnant(boolean pregnant, boolean sync) {
        this.isPregnant = pregnant;
        if (sync) {
            this.updateTracking();
        }
    }

    public int getCanLoveCooldown() {
        return this.canLoveCooldown;
    }

    public void setCanLoveCooldown(int canLoveCooldown, boolean sync) {
        this.canLoveCooldown = canLoveCooldown;
        if (sync) {
            this.updateTracking();
        }
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();

        tag.putInt("inLove",this.inLove);
        if (this.loveCause != null) {
            tag.putUUID("LoveCause", this.loveCause);
        }
        tag.putBoolean("isPregnant", this.isPregnant);
        tag.putInt("canLoveCooldown", this.canLoveCooldown);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        this.inLove = nbt.getInt("inLove");
        this.loveCause = nbt.hasUUID("LoveCause") ? nbt.getUUID("LoveCause") : null;
        this.isPregnant = nbt.getBoolean("isPregnant");
        this.canLoveCooldown = nbt.getInt("canLoveCooldown");
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.livingEntity.getId(), FishBreedingCapAttacher.LET_FISH_LOVE_CAP_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return FishBreedingCapAttacher.channel;
    }
}