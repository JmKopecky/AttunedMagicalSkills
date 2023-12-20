package dev.prognitio.ams;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.prognitio.ams.networking.ModNetworking;
import dev.prognitio.ams.networking.SetSelectedClassCS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SelectClassScreen extends Screen {
    public SelectClassScreen(Component p_96550_) {
        super(p_96550_);
    }

    Window window;
    int width;
    int height;
    int midx;
    int midy;
    HashMap<String, ArrayList<Component>> tooltipLines = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> positions = new HashMap<>();
    ResourceLocation background = new ResourceLocation(Ams.MODID, "textures/gui/gui_background.png");

    @Override
    protected void init() {
        window = Minecraft.getInstance().getWindow();
        width = window.getGuiScaledWidth();
        height = window.getGuiScaledHeight();
        midx = width/2;
        midy = height/2;

        ArrayList<Component> necromancyLines = new ArrayList<>();
        necromancyLines.add(Component.literal("nec line 1"));
        necromancyLines.add(Component.literal("nec line 2"));
        tooltipLines.put("Necromancer", necromancyLines);

        ArrayList<Component> summonerLines = new ArrayList<>();
        summonerLines.add(Component.literal("sum line 1"));
        summonerLines.add(Component.literal("sum line 2"));
        tooltipLines.put("Summoner", summonerLines);
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);

        RenderSystem.setShaderTexture(0, background);
        blit(stack, (width - 256)/2, (height-256)/2, 0, 0, 0, 256, 256, 256, 256);


        renderClass("Necromancer", true, midy, mouseX, mouseY, stack);
        renderClass("Summoner", false, midy, mouseX, mouseY, stack);

        super.render(stack, mouseX, mouseX, partialTicks);
    }

    private void renderClass(String name, boolean isDark, int yPos, int mouseX, int mouseY, PoseStack stack) {
        int textWidth = this.font.width(name);
        int xPos = isDark ? midx - (midx/4) - textWidth/2 : midx + (midx/4) - textWidth/2;
        int color = isDark ? 0xff36013f : 0xffddb500;
        this.font.draw(stack, Component.literal(name), xPos, yPos, color);

        HashMap<String, Integer> pos = new HashMap<>();
        pos.put("x1", xPos);
        pos.put("x2", xPos + font.width(name));
        pos.put("y1", yPos);
        pos.put("y2", yPos + font.lineHeight);
        positions.put(name, pos);

        boolean isMouseInRange = mouseX >= xPos && mouseX <= xPos + font.width(name) &&
                mouseY >= yPos && mouseY <= yPos + font.lineHeight;
        if (isMouseInRange) {
            this.renderTooltip(stack, tooltipLines.get(name), Optional.empty(), mouseX, mouseY, font);
        }
    }

    @Override
    public boolean mouseClicked(double xPos, double yPos, int click) {
        for (Map.Entry<String, HashMap<String, Integer>> entry : positions.entrySet()) {
            HashMap<String, Integer> pos = entry.getValue();

            boolean isInRange = xPos >= pos.get("x1") && xPos <= pos.get("x2") && yPos >= pos.get("y1") && yPos <= pos.get("y2");

            if (isInRange) {
                //select the class
                ModNetworking.sendToServer(new SetSelectedClassCS(entry.getKey()));
                this.onClose();
                return super.mouseClicked(xPos, yPos, click);
            }
        }


        return super.mouseClicked(xPos, yPos, click);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
