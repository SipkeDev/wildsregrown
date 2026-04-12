package com.wildsregrown.gui.menu.builder;

import com.sipke.math.MathUtil;
import com.wildsregrown.gui.menu.builder.camera.CameraRender;
import com.wildsregrown.gui.menu.builder.widgets.ButtonWidget;
import com.wildsregrown.gui.menu.builder.widgets.ClimateWidget;
import com.wildsregrown.gui.menu.builder.widgets.CycleWidget;
import com.wildsregrown.world.builder.SpawnPicker;
import com.wildsregrown.world.builder.WorldAge;
import com.wildsregrown.world.builder.WorldSize;
import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.menu.builder.camera.WorldTypeCamera;
import com.wildsregrown.world.builder.WorldType;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;

import java.util.Random;

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.WildsRegrown.modid;

public class WorldTypeScreen extends Screen {

    private final Identifier background = Identifier.of(modid, "gen_world_type");
    private final WorldBuilder builder;
    private final Screen parent;
    private final WorldTypeCamera camera;
    private TextFieldWidget worldName;

    public WorldTypeScreen(WorldBuilder builder, Screen parent){
        super(Text.literal("world_type"));
        this.builder = builder;
        this.parent = parent;
        this.camera = new WorldTypeCamera(MathUtil.min(this.client.getWindow().getScaledWidth(),this.client.getWindow().getScaledHeight()));
        this.camera.setRender(CameraRender.climate);
        this.camera.takeShot(builder.ctx);
    }

    public WorldTypeCamera getCamera() {
        return camera;
    }

    protected void init(){

        this.addDrawable(camera);

        int y = 2, m = 22, dx = -180;
        int w = 160,h = 20;

        this.worldName = new TextFieldWidget(textRenderer, this.width + dx, y+=m, w , h, Text.literal(builder.ctx.name));
        this.worldName.setText(builder.ctx.name);
        this.worldName.setTooltip(Tooltip.of(Text.literal("Set world name")));
        this.worldName.setChangedListener((input)-> this.builder.ctx.name = input);
        this.addDrawableChild(worldName);

        this.addDrawableChild(ButtonWidget.builder(Text.literal(String.valueOf(this.builder.ctx.seed)), (button) -> {
            this.builder.ctx.seed = new Random().nextInt();
            button.setMessage(Text.literal(String.valueOf(this.builder.ctx.seed)));
            this.camera.takeShot(builder.ctx);
        }).tooltip(Tooltip.of(Text.literal("Set world seed"))).dimensions(this.width  + dx, y+=m, w, h).build());

        this.addDrawableChild(CycleWidget.builder(GameMode::getSimpleTranslatableName).values(GameMode.values()).initially(GameMode.CREATIVE).build(this.width + dx, y+=m, w, h, Text.literal("Gamemode"), (button, type) -> {this.builder.ctx.gamemode = type.getIndex();})).setTooltip(Tooltip.of(Text.literal("Set the gamemode")));
        this.addDrawableChild(ClimateWidget.builder(SpawnPicker::getDisplayText).values(SpawnPicker.values()).initially(SpawnPicker.steppe).build(this.width + dx, y+=m, w, h, Text.literal("Spawn"), (button, type) -> {this.builder.ctx.spawnClimate = type.getClimate();})).setTooltip(Tooltip.of(Text.literal("Set world spawn")));
        this.addDrawableChild(CycleWidget.builder(WorldType::getDisplayText).values(WorldType.values()).initially(WorldType.continent).build(this.width + dx, y+=m, w, h, Text.literal("World Type"), (button, type) -> {this.builder.ctx.type = type.getType();this.camera.takeShot(this.builder.ctx);})).setTooltip(Tooltip.of(Text.literal("Set world type")));
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Settings"), (button) -> this.client.setScreen(new ConfigScreen(builder, this))).dimensions(this.width + dx, y+=m, w , h).tooltip(Tooltip.of(Text.of("Settings"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Build"), (button) -> this.client.setScreen(new ConfirmBuildScreen(builder, this))).dimensions(this.width + dx, y+=m, w , h).tooltip(Tooltip.of(Text.of("Build world"))).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> this.client.setScreen(parent)).dimensions(this.width + dx, y+=m, w , h).tooltip(Tooltip.of(Text.literal("Main menu"))).build());
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

}
