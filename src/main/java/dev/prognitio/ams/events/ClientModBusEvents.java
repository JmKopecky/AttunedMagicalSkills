package dev.prognitio.ams.events;

import dev.prognitio.ams.Ams;
import dev.prognitio.ams.networking.Keybinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ams.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModBusEvents {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(Keybinding.OPEN_MENU_KEY);
        event.register(Keybinding.SKILL_ACTIVATE_KEY);
    }
}
