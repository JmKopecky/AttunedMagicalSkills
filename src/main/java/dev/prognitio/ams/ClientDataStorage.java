package dev.prognitio.ams;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientDataStorage {
    private static AttunementClass playerAttunementClass;

    private static boolean hasPlayerChosen;

    private static int cooldown;
    private static int maxCooldown;

    public static int getCooldown() {
        return cooldown;
    }
    public static int getMaxCooldown() {
        return maxCooldown;
    }
    public static boolean getHasPlayerChosen() {
        return hasPlayerChosen;
    }
    public static AttunementClass getAttClass() {
        return playerAttunementClass;
    }

    public static void setCooldownData(String data) {
        cooldown = Integer.parseInt(data.split(":")[0]);
        maxCooldown = Integer.parseInt(data.split(":")[1]);
    }

    public static void setPlayerAttunementClass(String data) {
        hasPlayerChosen = Boolean.parseBoolean(data);
    }

    public static void setHasPlayerChosen(String data) {
        playerAttunementClass = AttunementClass.fromString(data);
    }
}
