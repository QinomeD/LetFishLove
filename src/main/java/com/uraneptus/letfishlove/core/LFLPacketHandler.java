package com.uraneptus.letfishlove.core;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class LFLPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel FISH_IN_LOVE_PACKET = NetworkRegistry.newSimpleChannel(LetFishLoveMod.modPrefix("fish_in_love"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public void init() {
        //FISH_IN_LOVE_PACKET.registerMessage(0, SyncPacket.class, SyncPacket::encode, LFLPacketHandler::receive, TierSortingRegistry::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

    }

    private static SyncPacket receive(FriendlyByteBuf buffer) {
        return new SyncPacket(buffer.readVarInt());
    }

    private record SyncPacket(int inLoveAmount) {
        private void encode(FriendlyByteBuf buffer) {
            buffer.writeVarInt(inLoveAmount);
        }
    }
}
