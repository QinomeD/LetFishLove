package com.uraneptus.letfishlove.common.network;

import com.uraneptus.letfishlove.LetFishLoveMod;
import com.uraneptus.letfishlove.common.capabilities.AbstractFishCap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class FishInLoveS2CMessage {
    private static int inLoveAmount;

    public FishInLoveS2CMessage(int inLoveAmount) {
        FishInLoveS2CMessage.inLoveAmount = inLoveAmount;
    }

    public static FishInLoveS2CMessage deserialize(FriendlyByteBuf buf) {
        return new FishInLoveS2CMessage(buf.readInt());
    }

    public static void handle(FishInLoveS2CMessage message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            LetFishLoveMod.CHANNEL.sendTo(new FishInLoveS2CMessage(getInLoveAmount()), context.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            context.setPacketHandled(true);
        }
    }

    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(inLoveAmount);
    }

    public static void setInLoveAmount(AbstractFish fish) {
        AbstractFishCap.getCapOptional(fish).ifPresent(cap -> inLoveAmount = cap.inLove);
    }

    public static int getInLoveAmount() {
        return inLoveAmount;
    }
}
