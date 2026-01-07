package com.wildsregrown.gui.map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class WorldMapScreen extends Screen{

    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final WorldMapScreen INSTANCE = new WorldMapScreen();
    public static boolean active;

    public WorldMapScreen() {
      super(Text.of("wrg.worldmap.title"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context,mouseX,mouseY,delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.of("Future WorldMap feature, need (decent) way to load the grid here."), this.width/2, this.height/2, Color.DARK_GRAY.getRGB());
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.applyBlur(context);
    }

    public static void show(){
        if (client.world != null) {
            if (!active && !client.inGameHud.getChatHud().isChatFocused()) {
                //Activate instance
                active = true;
                client.setScreen(INSTANCE);
            }
        }
    }

    public static void hide() {
        if (client.world != null) {
            if (active) {
                //Disable instance
                active = false;
                client.setScreen(null);
            }
        }
    }

    //behaviour
    @Override
    public boolean shouldPause() {
        return false;
    }

}