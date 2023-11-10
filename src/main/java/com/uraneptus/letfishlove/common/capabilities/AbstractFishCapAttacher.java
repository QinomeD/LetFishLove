package com.uraneptus.letfishlove.common.capabilities;

import com.uraneptus.letfishlove.LetFishLoveMod;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import dev._100media.capabilitysyncer.core.LivingEntityCapability;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

public class AbstractFishCapAttacher  extends CapabilityAttacher {
    public static SimpleChannel channel;
    private static final Class<LivingEntityCapability> CAPABILITY_CLASS = LivingEntityCapability.class;
    public static final Capability<LivingEntityCapability> ABSTRACT_FISH_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation ABSTRACT_FISH_CAPABILITY_RL = LetFishLoveMod.modPrefix("abstract_fish_cap");

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static LivingEntityCapability getExampleLivingEntityCapabilityUnwrap(AbstractFish entity) {
        return getAbstractFishCapability(entity).orElse(null);
    }

    public static LazyOptional<LivingEntityCapability> getAbstractFishCapability(AbstractFish entity) {
        return entity.getCapability(ABSTRACT_FISH_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, AbstractFish entity) {
        genericAttachCapability(event, new AbstractFishCap(entity), ABSTRACT_FISH_CAPABILITY, ABSTRACT_FISH_CAPABILITY_RL);
    }

    public static void setupChannel() {
        channel = NetworkRegistry.newSimpleChannel(LetFishLoveMod.modPrefix("abstract_fish_cap_channel"), () -> "1", s -> true, s -> true);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(AbstractFish.class, AbstractFishCapAttacher::attach, AbstractFishCapAttacher::getAbstractFishCapability, true);
        SimpleEntityCapabilityStatusPacket.register(channel, 0);
        SimpleEntityCapabilityStatusPacket.registerRetriever(ABSTRACT_FISH_CAPABILITY_RL, AbstractFishCapAttacher::getExampleLivingEntityCapabilityUnwrap);
    }

}
