package com.wildsregrown.gui.menu.main;

import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.menu.SkunkWorksScreen;
import com.wildsregrown.gui.menu.builder.WorldTypeScreen;
import com.wildsregrown.gui.menu.world.SelectWorld;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class MainMenu extends Screen {

    private final Identifier background = Identifier.of(modid, "background");

    public MainMenu() {
        super(Text.of("narrator.screen.title"));
    }

    protected void init(){

        int y = height/5, m = 22, dx = -124;

        this.addDrawableChild(MainButtonWidget.builder(Text.literal("New World"), (button) -> this.client.setScreen(new WorldTypeScreen(new WorldBuilder(), this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addDrawableChild(MainButtonWidget.builder(Text.literal("Load World"), (button) -> this.client.setScreen(new SelectWorld(this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addDrawableChild(MainButtonWidget.builder(Text.literal("Multiplayer"), (button) -> this.client.setScreen(new MultiplayerScreen(this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addDrawableChild(MainButtonWidget.builder(Text.literal("Options"), (button) -> this.client.setScreen(new OptionsScreen(this, this.client.options))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addDrawableChild(MainButtonWidget.builder(Text.literal("inDev"), (button) -> this.client.setScreen(new SkunkWorksScreen(this))).dimensions(this.width  + dx, y+=m, 100, 20).build());
        this.addDrawableChild(MainButtonWidget.builder(Text.literal("Quit Game"), (button) -> this.client.scheduleStop()).dimensions(this.width + dx, y+=m, 100, 20).build());

    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

    public void addButton(MainButtonWidget injectedButton) {
        this.addDrawableChild(injectedButton);
    }
}
