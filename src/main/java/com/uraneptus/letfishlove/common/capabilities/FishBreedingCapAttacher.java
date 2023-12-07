package com.uraneptus.letfishlove.common.capabilities;

import com.uraneptus.letfishlove.LetFishLoveMod;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import dev._100media.capabilitysyncer.core.LivingEntityCapability;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

public class FishBreedingCapAttacher extends CapabilityAttacher {
    public static SimpleChannel channel;
    private static final Class<LivingEntityCapability> CAPABILITY_CLASS = LivingEntityCapability.class;
    public static final Capability<LivingEntityCapability> FISH_BREEDING_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation LET_FISH_LOVE_CAP_RL = LetFishLoveMod.modPrefix("let_fish_love_cap");

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static LivingEntityCapability getExampleLivingEntityCapabilityUnwrap(WaterAnimal entity) {
        return getFishBreedingCapability(entity).orElse(null);
    }

    public static LazyOptional<LivingEntityCapability> getFishBreedingCapability(WaterAnimal entity) {
        return entity.getCapability(FISH_BREEDING_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, WaterAnimal entity) {
        genericAttachCapability(event, new FishBreedingCap(entity), FISH_BREEDING_CAPABILITY, LET_FISH_LOVE_CAP_RL);
    }

    public static void setupChannel() {
        channel = NetworkRegistry.newSimpleChannel(LetFishLoveMod.modPrefix("let_fish_love_cap_channel"), () -> "1", s -> true, s -> true);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(WaterAnimal.class, FishBreedingCapAttacher::attach, FishBreedingCapAttacher::getFishBreedingCapability, true);
        SimpleEntityCapabilityStatusPacket.register(channel, 0);
        SimpleEntityCapabilityStatusPacket.registerRetriever(LET_FISH_LOVE_CAP_RL, FishBreedingCapAttacher::getExampleLivingEntityCapabilityUnwrap);
    }

}
