package dev.prognitio.ams.networking;

import dev.prognitio.ams.ACDataProvider;
import dev.prognitio.ams.AttunementClass;
import dev.prognitio.ams.AttunementClassDefaults;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSelectedClassCS {

    private final String data;

    public SetSelectedClassCS(String data) {
        this.data = data;
    }

    public SetSelectedClassCS(FriendlyByteBuf buf) {
        this.data = buf.readComponent().getString();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(data));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //on the server
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getCapability(ACDataProvider.AMSDATA).ifPresent(cap -> {
                    AttunementClassDefaults target = AttunementClassDefaults.getFromString(data.toLowerCase());
                    if (target != null) {
                        AttunementClass newClass = new AttunementClass(target);
                        cap.setAttunementClass(player, newClass);
                    } else {
                        System.out.println("Error in SetSelectedClassCS: " + data + " does not match a known AttunementClassDefault");
                        throw new IllegalArgumentException("Error in SetSelectedClassCS: " + data + " does not match a known AttunementClassDefault");
                    }
                });
            }
        });
        return true;
    }

}
