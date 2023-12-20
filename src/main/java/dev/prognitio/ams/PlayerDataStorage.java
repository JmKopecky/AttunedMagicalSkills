package dev.prognitio.ams;

import dev.prognitio.ams.networking.ModNetworking;
import dev.prognitio.ams.networking.SyncAttunedClassDataSC;
import dev.prognitio.ams.networking.SyncCooldownSC;
import dev.prognitio.ams.networking.SyncIsDataSetSC;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.ConnectionData;

public class PlayerDataStorage {


    //most of the important stuff regarding the player's attunement class
    private AttunementClass playerAttunementClass;
    private boolean hasSelectionItemBeenGiven = false;

    public void setHasSelectionItemBeenGiven(boolean val) {
        hasSelectionItemBeenGiven = val;
    }

    public boolean getHasSelectionItemBeenGiven() {
        return hasSelectionItemBeenGiven;
    }

    public void setAttunementClass(Player player, AttunementClass attunementClass) {
        this.playerAttunementClass = attunementClass;
        this.hasPlayerChosen = true;
        syncData(player);
    }

    public AttunementClass getAttunementClass() {
        if (playerAttunementClass != null) {
            return playerAttunementClass;
        }
        return null;
    }

    //checks to make sure player has chosen a class
    private boolean hasPlayerChosen = false;

    public void setPlayerChosen(boolean value) {
        hasPlayerChosen = value;
    }
    public boolean getPlayerChosen() {
        return hasPlayerChosen;
    }

    //cooldown management stuff
    private int cooldown = 0;
    private int recentMaxCooldown = 0;

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setRecentMaxCooldown(int maxCooldown) {
        this.recentMaxCooldown = maxCooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }
    public int getRecentMaxCooldown() {
        return this.recentMaxCooldown;
    }

    //when packet has been sent to trigger the skill
    public void attemptTriggerSkill(Player player) {
        if (cooldown <= 0) {
            int result = playerAttunementClass.triggerActiveSkill(player);
            if (result != -1) {
                cooldown = result;
                recentMaxCooldown = result;
            }
        }
        syncData(player);
    }



    public void copyFrom(PlayerDataStorage source) {
        this.hasPlayerChosen = source.hasPlayerChosen;
        this.hasSelectionItemBeenGiven = source.hasSelectionItemBeenGiven;
        this.cooldown = source.cooldown;
        this.recentMaxCooldown = source.recentMaxCooldown;
        if (source.getAttunementClass() != null) {
            this.playerAttunementClass = source.getAttunementClass();
        }
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putBoolean("hasplayerchosen", hasPlayerChosen);
        tag.putBoolean("hasitembeengiven", hasSelectionItemBeenGiven);
        tag.putInt("cooldown", cooldown);
        tag.putInt("maxcooldown", recentMaxCooldown);
        if (playerAttunementClass != null) {
            tag.putString("attuneclass", playerAttunementClass.toString());
        }
    }

    public void loadNBTData(CompoundTag tag) {
        this.hasPlayerChosen = tag.getBoolean("hasplayerchosen");
        this.hasSelectionItemBeenGiven = tag.getBoolean("hasitembeengiven");
        this.cooldown = tag.getInt("cooldown");
        this.recentMaxCooldown = tag.getInt("maxcooldown");
        String attClassStr = tag.getString("attuneclass");
        if (attClassStr.equals("")) {
            AttunementClass newClass = AttunementClass.fromString(tag.getString("attuneclass"));
            if (newClass != null) {
                playerAttunementClass = newClass;
            }
        }
    }

    public void syncData(Player player) {
        if (playerAttunementClass == null) {
            return;
        }
        String data = cooldown + ":" + recentMaxCooldown;
        ModNetworking.sendToPlayer(new SyncCooldownSC(data), (ServerPlayer) player);
        data = "" + hasPlayerChosen;
        ModNetworking.sendToPlayer(new SyncIsDataSetSC(data), (ServerPlayer) player);
        data = playerAttunementClass.toString();
        ModNetworking.sendToPlayer(new SyncAttunedClassDataSC(data), (ServerPlayer) player);
    }
}
