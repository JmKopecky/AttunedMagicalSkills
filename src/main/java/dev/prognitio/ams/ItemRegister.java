package dev.prognitio.ams;

import dev.prognitio.ams.networking.ModNetworking;
import dev.prognitio.ams.networking.OpenSelectionMenuSC;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ams.MODID);

    public static final RegistryObject<Item> RUNE_OF_FORETELLING = ITEMS.register(
            "rune_of_foretelling", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
            {
                @Override
                public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
                    if (!level.isClientSide()){
                        ModNetworking.sendToPlayer(new OpenSelectionMenuSC(), (ServerPlayer) user);
                    }
                    return InteractionResultHolder.consume(user.getItemInHand(hand));
                }
            }
    );
}
