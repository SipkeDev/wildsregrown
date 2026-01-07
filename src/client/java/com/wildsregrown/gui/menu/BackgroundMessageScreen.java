package com.wildsregrown.gui.menu;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BackgroundMessageScreen extends MessageScreen {

    private final Identifier background;

    public BackgroundMessageScreen(Text text, Identifier background) {
        super(text);
        this.background = background;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
