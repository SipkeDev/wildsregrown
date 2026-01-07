package com.wildsregrown.gui.menu.world;

import com.sipke.api.grid.WorldGrid;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.gui.menu.builder.camera.CameraRender;
import com.wildsregrown.gui.menu.builder.camera.GridCamera;
import com.wildsregrown.gui.menu.builder.widgets.ButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


import static com.wildsregrown.WildsRegrown.modid;

public class PreviewWorld extends Screen {

    public static PreviewWorld create(MinecraftClient client, WorldGrid grid){
        return new PreviewWorld(client, grid);
    }

    private final Identifier background = Identifier.of(modid, "gen_world_type");
    private final MinecraftClient client;
    private final WorldGrid grid;
    private final GridCamera camera;

    private PreviewWorld(MinecraftClient client, WorldGrid grid) {
        super(Text.of("Preview"));
        this.client = client;
        this.grid = grid;
        this.camera = new GridCamera(this, grid.getSize());
        this.camera.takeShot(grid);
    }

    protected void init(){
        this.addDrawable(camera);

        int y = 8, m = 18, dx = -124;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Region"),          (button) -> {this.camera.setRender(CameraRender.region);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Height"),          (button) -> {this.camera.setRender(CameraRender.height);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Elevation"),          (button) -> {this.camera.setRender(CameraRender.elevation);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Temperature"),     (button) -> {this.camera.setRender(CameraRender.temperature);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Moisture"),        (button) -> {this.camera.setRender(CameraRender.moisture);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Landform"),        (button) -> {this.camera.setRender(CameraRender.landform);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Landform Edge"),   (button) -> {this.camera.setRender(CameraRender.landform_edge);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Ecosystem Edge"),  (button) -> {this.camera.setRender(CameraRender.ecosystem_edge);this.camera.takeShot(grid);}).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> this.client.setScreen(new SelectWorld(new MainMenu()))).dimensions(this.width + dx, y+=m, 100 , 20).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
