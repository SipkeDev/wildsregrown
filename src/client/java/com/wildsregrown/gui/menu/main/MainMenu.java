package com.wildsregrown.gui.menu.main;

import com.sipke.builder.WorldBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import wildsregrown.api.client.gui.menu.builder.WorldTypeScreen;
import wildsregrown.api.client.gui.menu.world.SelectWorld;

import static com.wildsregrown.WildsRegrown.modid;

public class MainMenu extends Screen {

    private final Identifier background = Identifier.fromNamespaceAndPath(modid, "background");

    public MainMenu() {
        super(Component.nullToEmpty("narrator.screen.title"));
    }

    protected void init(){

        int y = height/5, m = 22, dx = -124;

        this.addRenderableWidget(MainButtonWidget.builder(Component.literal("New World"), (button) -> this.minecraft.setScreen(new WorldTypeScreen(new WorldBuilder(), this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addRenderableWidget(MainButtonWidget.builder(Component.literal("Load World"), (button) -> this.minecraft.setScreen(new SelectWorld(this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addRenderableWidget(MainButtonWidget.builder(Component.literal("Multiplayer"), (button) -> this.minecraft.setScreen(new JoinMultiplayerScreen(this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addRenderableWidget(MainButtonWidget.builder(Component.literal("Options"), (button) -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        //this.addRenderableWidget(MainButtonWidget.builder(Component.literal("default menu"), (button) -> this.minecraft.setScreen(new TitleScreen())).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addRenderableWidget(MainButtonWidget.builder(Component.literal("Quit Game"), (button) -> this.minecraft.stop()).dimensions(this.width + dx, y+=m, 100, 20).build());

    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

    public void addButton(MainButtonWidget injectedButton) {
        this.addRenderableWidget(injectedButton);
    }
}
