package dev.prognitio.ams.networking;

import dev.prognitio.ams.ClientDataStorage;
import dev.prognitio.ams.SelectClassScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncIsDataSetSC {

    private final String data;

    public SyncIsDataSetSC(String data) {
        this.data = data;
    }

    public SyncIsDataSetSC(FriendlyByteBuf buf) {
        this.data = buf.readComponent().getString();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(data));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDataStorage.setHasPlayerChosen(data);
        });
        return true;
    }

}
