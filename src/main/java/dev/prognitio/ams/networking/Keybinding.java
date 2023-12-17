package dev.prognitio.ams.networking;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class Keybinding {

    //translation key for category
    public static final String CATEGORY = "key.category.ams.skills";
    //translation for the key
    public static final String KEY_USE_SKILL = "key.ams.use_skill";
    public static final String KEY_OPEN_MENU = "key.ams.open_menu";

    public static final KeyMapping SKILL_ACTIVATE_KEY = new KeyMapping(KEY_USE_SKILL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);

    public static final KeyMapping OPEN_MENU_KEY = new KeyMapping(KEY_OPEN_MENU, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, CATEGORY);
}
