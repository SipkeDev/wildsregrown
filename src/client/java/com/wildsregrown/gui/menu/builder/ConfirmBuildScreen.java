package com.wildsregrown.gui.menu.builder;

import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.menu.builder.widgets.ButtonWidget;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.wildsregrown.WildsRegrown.modid;

public class ConfirmBuildScreen extends Screen {

    private final Identifier background = Identifier.of(modid, "background");
    private final WorldBuilder builder;
    private final Screen parent;

    public ConfirmBuildScreen(WorldBuilder builder, Screen parent){
        super(Text.literal("build"));
        this.builder = builder;
        this.parent = parent;
    }

    protected void init(){

        this.addDrawable(getTextWidget());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Build"), (button) -> {
            //Converts the context to a grid file and saves it along with an icon.
            new IntegratedBuilder(client, builder).start();
        }).dimensions((this.width/2) - 102, this.height-32, 100 , 20).tooltip(Tooltip.of(Text.literal("Builds world grid\nMay take a while, application will pretend to be frozen"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> this.client.setScreen(parent))
                .dimensions((this.width/2) + 2, this.height-32, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
    }

    private @NotNull MultilineTextWidget getTextWidget() {
        Text text = Text.of(
                "World building takes a while to finish. \n" +
                        "The application may pretend to be frozen for a period. \n" +
                        "It uses all available CPU cores, having more greatly improves build times. \n" +
                        "Using less than 6GB may cause it to lock and fail."
        );
        MultilineTextWidget widget = new MultilineTextWidget((parent.width/2)-200, (parent.height/2), text, textRenderer);
        widget.setMaxWidth(400);
        widget.setCentered(true);
        widget.setAlpha(1f);
        return widget;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
