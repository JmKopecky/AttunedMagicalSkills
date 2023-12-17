package dev.prognitio.ams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttunementClass {


    public static final int MAX_ATT_CLASS_LEVEL = 10;

    private AttunementClassDefaults defaultData;
    private int level = 1;
    private int xp = 0;
    private int attunementDamageBonus = 0; //positive is light magic; effective against undead. negative is dark magic; effective against the living
    private int cooldownWhenTriggered = 0;
    private HashMap<String, Double> passiveBonuses = new HashMap<>();

    public AttunementClass(AttunementClassDefaults defaultData) {
        this.defaultData = defaultData;
        attunementDamageBonus = defaultData.getAttunementDamageScaling() * level;
        passiveBonuses.putAll(this.defaultData.getPassiveBonuses());
        cooldownWhenTriggered = defaultData.getCooldownWhenTriggered();
    }


    public void addXp(int xpAmount) {
        if (xp + xpAmount >= getReqXpToLevel()) {
            //try level up
            if (level >= MAX_ATT_CLASS_LEVEL) {
                //already max level
                xp = getReqXpToLevel();
            } else {
                //do level up
                xp += xpAmount;
                xp -= getReqXpToLevel();
                levelUp();
            }
        } else {
            this.xp += xpAmount;
        }
    }

    public int getReqXpToLevel() {
        int initialXPReq = 1000;
        double perLevelScaleVal = 1.35;
        return (int) (initialXPReq * Math.pow(perLevelScaleVal, level));
    }

    public void levelUp() {
        level++;

        attunementDamageBonus = defaultData.getAttunementDamageScaling() * level;

        ArrayList<String> passiveKeys = new ArrayList<>();
        for (Map.Entry<String, Double> entry : passiveBonuses.entrySet()) {
            passiveKeys.add(entry.getKey());
        }
        for (String key : passiveKeys) {
            passiveBonuses.put(key, level * defaultData.getPassiveBonuses().get(key));
        }

        cooldownWhenTriggered = defaultData.getCooldownWhenTriggered() + defaultData.getCooldownScale() * (level - 1);
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public int getAttunementDamageBonus() {
        return attunementDamageBonus;
    }

    public HashMap<String, Double> getPassiveBonuses() {
        return passiveBonuses;
    }

    public double getPassiveIfPresent(String key) {
        if (passiveBonuses.containsKey(key)) {
            return passiveBonuses.get(key);
        } else {
            return -1;
        }
    }

    public int triggerActiveSkill(Player player) {
        switch (defaultData.getId()) {
            case "necromancer" -> {
                SkillTriggerMethods.necromancer(player, level);
                return cooldownWhenTriggered;
            }
        }
        return -1;
    }

    public static AttunementClass fromString(String GSON) {
        return ((new GsonBuilder()).create()).fromJson(GSON, AttunementClass.class);
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

}
