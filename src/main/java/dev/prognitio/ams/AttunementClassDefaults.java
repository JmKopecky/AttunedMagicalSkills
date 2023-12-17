package dev.prognitio.ams;

import java.util.HashMap;
import java.util.Map;

public enum AttunementClassDefaults {

    NECROMANCER("necromancer", "Necromancer", "Summon the undead to fight for you.", -1,
            new HashMap<>(Map.of("", 2.0, "", 1.0)),
            10, -1
    );



    private String id;
    private String displayName;
    private String desc;
    private int attunementDamageScaling;
    private int cooldownWhenTriggered;
    private int cooldownScale;
    private HashMap<String, Double> passiveBonuses;

    AttunementClassDefaults(String id, String displayName, String desc, int attunementDamageScaling, HashMap<String, Double> passiveBonuses, int cooldownWhenTriggered, int cooldownScale) {
        this.id = id;
        this.displayName = displayName;
        this.desc = desc;
        this.attunementDamageScaling = attunementDamageScaling;
        this.passiveBonuses = passiveBonuses;
        this.cooldownWhenTriggered = cooldownWhenTriggered;
        this.cooldownScale = cooldownScale;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getId() {
        return this.id;
    }

    public int getAttunementDamageScaling() {
        return this.attunementDamageScaling;
    }

    public int getCooldownWhenTriggered() {return this.cooldownWhenTriggered;}

    public int getCooldownScale() {return this.cooldownScale;}

    public HashMap<String, Double> getPassiveBonuses() {
        return this.passiveBonuses;
    }

    /*
    Passive Bonuses:
        antihuman: bonus damage to villagers and (MobType == ILLAGER)
     */

    /*
    Lore:
        Necromancer:

        Summoner:

     */
}
