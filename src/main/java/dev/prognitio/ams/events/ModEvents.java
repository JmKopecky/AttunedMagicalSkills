package dev.prognitio.ams.events;

import dev.prognitio.ams.ACDataProvider;
import dev.prognitio.ams.Ams;
import dev.prognitio.ams.ItemRegister;
import dev.prognitio.ams.PlayerDataStorage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ams.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (event.getObject() != null) {
                if (!(event.getObject()).getCapability(ACDataProvider.AMSDATA).isPresent()) {
                    event.addCapability(new ResourceLocation(Ams.MODID, "attuned_magic_skills"), new ACDataProvider());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(ACDataProvider.AMSDATA).ifPresent(original -> event.getEntity().getCapability(ACDataProvider.AMSDATA).ifPresent(cloned -> {
                cloned.copyFrom(original);
            }));
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerDataStorage.class);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getCapability(ACDataProvider.AMSDATA).ifPresent(cap -> {
            cap.syncData(event.getEntity());
            if (!cap.getPlayerChosen()) {
                if (!cap.getHasSelectionItemBeenGiven()) {
                    event.getEntity().addItem(new ItemStack(ItemRegister.RUNE_OF_FORETELLING.get()));
                    cap.setHasSelectionItemBeenGiven(true);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        LivingEntity source = null;
        Player pSource = null;
        if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player) {
            if (event.getSource().getEntity() instanceof Player) {
                pSource = (Player) event.getSource().getEntity();
            } else {
                source = (LivingEntity) event.getSource().getEntity();
            }
        }

        if (pSource != null) {
            pSource.getCapability(ACDataProvider.AMSDATA).ifPresent(cap -> {
                if (cap.getPlayerChosen()) {


                    //do damage bonus based on attunement
                    int attBonus = cap.getAttunementClass().getAttunementDamageBonus();
                    if (attBonus < 0) { //dark attunement
                        if (target.getMobType() != MobType.UNDEAD) {
                            event.setAmount(event.getAmount() + Math.abs(attBonus)); //deal bonus damage to undead
                        }
                    } else if (attBonus > 0) { //light attunement
                        if (target.getMobType() == MobType.UNDEAD) {
                            event.setAmount(event.getAmount() + Math.abs(attBonus)); //deal bonus damage to undead
                        }
                    }


                }

            });

        }
    }
}
