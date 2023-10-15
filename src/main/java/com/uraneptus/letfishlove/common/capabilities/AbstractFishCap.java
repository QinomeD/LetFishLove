package com.uraneptus.letfishlove.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.UUID;

public class AbstractFishCap implements INBTSerializable<CompoundTag> {

    public static Capability<AbstractFishCap> ENTITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public int inLove;
    @Nullable
    public UUID loveCause;

    public AbstractFishCap() {}

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putInt("inLove", inLove);
        if (this.loveCause != null) {
            tag.putUUID("LoveCause", this.loveCause);
        }

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inLove = nbt.getInt("inLove");
        this.loveCause = nbt.hasUUID("LoveCause") ? nbt.getUUID("LoveCause") : null;
    }

    public static LazyOptional<AbstractFishCap> getCapOptional(LivingEntity entity) {
        return entity.getCapability(ENTITY_CAPABILITY);
    }

    @Mod.EventBusSubscriber
    public static class Events {

        @SubscribeEvent
        public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof AbstractFish) {
                final AbstractFishCapProvider provider = new AbstractFishCapProvider();
                event.addCapability(AbstractFishCapProvider.LOCATION, provider);
            }
        }
    }
}