package com.wildsregrown.gui.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class SkunkWorksScreen extends Screen {

    private final Identifier background = Identifier.fromNamespaceAndPath(modid, "background");
    private final Screen parent;

    public SkunkWorksScreen(Screen parent) {
        super(Component.nullToEmpty("narrator.screen.credits"));
        this.parent = parent;
    }

    protected void init(){

        this.addRenderableOnly(new StringWidget(Component.nullToEmpty("This is where random things are tested"), font));

        this.addRenderableWidget(Button.builder(Component.literal("Back"), (button) -> this.minecraft.setScreen(parent)).bounds(this.width/2-50, this.height-22, 100, 20).build());

    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
