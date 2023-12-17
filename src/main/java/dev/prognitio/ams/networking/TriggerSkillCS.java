package dev.prognitio.ams.networking;

import dev.prognitio.ams.ACDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerSkillCS {

    public TriggerSkillCS() {

    }

    public TriggerSkillCS(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //on the server
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getCapability(ACDataProvider.AMSDATA).ifPresent(cap -> cap.attemptTriggerSkill(player));
            }
        });
        return true;
    }
}
