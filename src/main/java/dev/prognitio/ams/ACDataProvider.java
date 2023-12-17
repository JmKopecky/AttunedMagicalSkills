package dev.prognitio.ams;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ACDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    public static Capability<PlayerDataStorage> AMSDATA = CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerDataStorage amsDataInstance = null;

    private final LazyOptional<PlayerDataStorage> optional = LazyOptional.of(this::createAMSData);

    private PlayerDataStorage createAMSData() {
        if (this.amsDataInstance == null) {
            this.amsDataInstance = new PlayerDataStorage();
        }
        return this.amsDataInstance;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == AMSDATA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createAMSData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAMSData().loadNBTData(nbt);
    }

}
