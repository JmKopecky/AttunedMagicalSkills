package dev.prognitio.ams.networking;

import dev.prognitio.ams.Ams;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {

    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Ams.MODID, "networking"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(TriggerSkillCS.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TriggerSkillCS::new)
                .encoder(TriggerSkillCS::toBytes)
                .consumerMainThread(TriggerSkillCS::handle).add();

        net.messageBuilder(SetSelectedClassCS.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSelectedClassCS::new)
                .encoder(SetSelectedClassCS::toBytes)
                .consumerMainThread(SetSelectedClassCS::handle).add();

        net.messageBuilder(SyncCooldownSC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncCooldownSC::new)
                .encoder(SyncCooldownSC::toBytes)
                .consumerMainThread(SyncCooldownSC::handle).add();

        net.messageBuilder(SyncIsDataSetSC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncIsDataSetSC::new)
                .encoder(SyncIsDataSetSC::toBytes)
                .consumerMainThread(SyncIsDataSetSC::handle).add();

        net.messageBuilder(SyncAttunedClassDataSC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncAttunedClassDataSC::new)
                .encoder(SyncAttunedClassDataSC::toBytes)
                .consumerMainThread(SyncAttunedClassDataSC::handle).add();

        net.messageBuilder(OpenSelectionMenuSC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenSelectionMenuSC::new)
                .encoder(OpenSelectionMenuSC::toBytes)
                .consumerMainThread(OpenSelectionMenuSC::handle).add();



    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
