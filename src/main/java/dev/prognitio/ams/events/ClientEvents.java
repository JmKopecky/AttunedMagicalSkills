package dev.prognitio.ams.events;

import dev.prognitio.ams.Ams;
import dev.prognitio.ams.SelectClassScreen;
import dev.prognitio.ams.networking.Keybinding;
import dev.prognitio.ams.networking.ModNetworking;
import dev.prognitio.ams.networking.TriggerSkillCS;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ams.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Keybinding.OPEN_MENU_KEY.consumeClick()) {
            //Minecraft.getInstance().setScreen(new SelectClassScreen(Component.literal("View Class Info Screen")));
        }
        if (Keybinding.SKILL_ACTIVATE_KEY.consumeClick()) {
            ModNetworking.sendToServer(new TriggerSkillCS());
        }
    }
}
