package com.uraneptus.letfishlove.common.capabilities;

import com.uraneptus.letfishlove.LetFishLoveMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractFishCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final ResourceLocation LOCATION = LetFishLoveMod.modPrefix("abstract_fish_cap");
    private final AbstractFishCap backend = new AbstractFishCap();
    private final LazyOptional<AbstractFishCap> optional = LazyOptional.of(() -> backend);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return AbstractFishCap.ENTITY_CAPABILITY.orEmpty(cap, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.backend.deserializeNBT(nbt);
    }

    public AbstractFishCapProvider() {}

}