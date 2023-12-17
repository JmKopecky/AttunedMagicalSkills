package dev.prognitio.ams;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerDataStorage {


    //most of the important stuff regarding the player's attunement class
    private AttunementClass playerAttunementClass;

    public void setAttunementClass(AttunementClass attunementClass) {
        this.playerAttunementClass = attunementClass;
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
    }



    public void copyFrom(PlayerDataStorage source) {
        this.hasPlayerChosen = source.hasPlayerChosen;
        if (source.getAttunementClass() != null) {
            this.playerAttunementClass = source.getAttunementClass();
        }
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putBoolean("hasplayerchosen", hasPlayerChosen);
    }

    public void loadNBTData(CompoundTag tag) {
        this.hasPlayerChosen = tag.getBoolean("hasplayerchosen");
    }
}
