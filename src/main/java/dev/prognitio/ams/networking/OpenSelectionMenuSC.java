package dev.prognitio.ams.networking;

import dev.prognitio.ams.ACDataProvider;
import dev.prognitio.ams.SelectClassScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public class OpenSelectionMenuSC {

    public OpenSelectionMenuSC() {

    }

    public OpenSelectionMenuSC(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new SelectClassScreen(Component.literal("Select Class Menu")));
        });
        return true;
    }

}
