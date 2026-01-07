package com.wildsregrown.gui.menu;

import com.wildsregrown.gui.menu.builder.widgets.ButtonWidget;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class SkunkWorksScreen extends Screen {

    private final Identifier background = Identifier.of(modid, "background");
    private final Screen parent;

    public SkunkWorksScreen(Screen parent) {
        super(Text.of("narrator.screen.credits"));
        this.parent = parent;
    }

    protected void init(){

        this.addDrawable(new TextWidget(Text.of("This is where random things are tested"), textRenderer));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> this.client.setScreen(parent)).dimensions(this.width/2-50, this.height-22, 100, 20).build());

    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
