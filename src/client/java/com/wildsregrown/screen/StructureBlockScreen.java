package com.wildsregrown.screen;

import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.network.payloads.StructureBlockPayload;
import com.wildsregrown.network.payloads.StructureSavePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static com.wildsregrown.gui.KeyBindings.hasKeyDown;


public class StructureBlockScreen extends Screen {

    private final int x;
    private final int y;

    private final int posx;
    private final int posy;
    private final int posz;
    private String name;
    private boolean show;
    private int x0;
    private int y0;
    private int z0;
    private int x1;
    private int y1;
    private int z1;

    private final int maxSize = 256;

    public StructureBlockScreen(StructureBlockPayload payload) {
        super(Text.of(payload.compound().getString("name","new_world")));
        this.posx = payload.compound().getInt("posx",0);
        this.posy = payload.compound().getInt("posy",0);
        this.posz = payload.compound().getInt("posz",0);
        this.name = payload.compound().getString("name", "new");
        this.show = payload.compound().getBoolean("show", false);
        this.x0 = payload.compound().getInt("x0",0);
        this.y0 = payload.compound().getInt("y0",0);
        this.z0 = payload.compound().getInt("z0",0);
        this.x1 = payload.compound().getInt("x1",0);
        this.y1 = payload.compound().getInt("y1",0);
        this.z1 = payload.compound().getInt("z1",0);

        this.x = this.width/2;
        this.y = this.height/2;
    }

    @Override
    protected void init() {
        int width = 80;
        int height = 20;

        TextWidget x0 = new TextWidget(Text.of(String.valueOf(this.x0)), this.client.textRenderer);
        x0.setDimensionsAndPosition(width, height, 40, 40);
        ButtonWidget x0Up = ButtonWidget.builder(Text.of("Pivot X up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x0 = MathUtil.clamp(this.x0 + 16, -(maxSize/2), 0);
            }else {
                this.x0 = MathUtil.clamp(this.x0 + 1, -(maxSize/2), 0);
            }
            x0.setMessage(Text.of(String.valueOf(this.x0)));
        }).dimensions(40, 60, width, height).build();
        ButtonWidget x0Down = ButtonWidget.builder(Text.of("Pivot X Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x0 = MathUtil.clamp(this.x0 - 16, -(maxSize/2), 0);
            }else {
                this.x0 = MathUtil.clamp(this.x0 - 1, -(maxSize/2), 0);
            }
            x0.setMessage(Text.of(String.valueOf(this.x0)));
        }).dimensions(40, 80, width, height).build();
        this.addDrawableChild(x0);
        this.addDrawableChild(x0Up);
        this.addDrawableChild(x0Down);

        TextWidget y0 = new TextWidget(Text.of(String.valueOf(this.y0)), this.client.textRenderer);
        y0.setDimensionsAndPosition(width, height, 120, 40);
        ButtonWidget y0Up = ButtonWidget.builder(Text.of("Pivot Y up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y0 = MathUtil.clamp(this.y0 + 16, -(maxSize/2), 0);
            }else {
                this.y0 = MathUtil.clamp(this.y0 + 1, -(maxSize/2), 0);
            }
            y0.setMessage(Text.of(String.valueOf(this.y0)));
        }).dimensions(120, 60, width, height).build();
        ButtonWidget y0Down = ButtonWidget.builder(Text.of("Pivot Y Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y0 = MathUtil.clamp(this.y0 - 16, -(maxSize/2), 0);
            }else {
                this.y0 = MathUtil.clamp(this.y0 - 1, -(maxSize/2), 0);
            }
            y0.setMessage(Text.of(String.valueOf(this.y0)));
        }).dimensions(120, 80, width, height).build();
        this.addDrawableChild(y0);
        this.addDrawableChild(y0Up);
        this.addDrawableChild(y0Down);

        TextWidget z0 = new TextWidget(Text.of(String.valueOf(this.z0)), this.client.textRenderer);
        z0.setDimensionsAndPosition(width, height, 200, 40);
        ButtonWidget z0Up = ButtonWidget.builder(Text.of("Pivot Z up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z0 = MathUtil.clamp(this.z0 + 16, -(maxSize/2), 0);
            }else {
                this.z0 = MathUtil.clamp(this.z0 + 1, -(maxSize/2), 0);
            }
            z0.setMessage(Text.of(String.valueOf(this.z0)));
        }).dimensions(200, 60, width, height).build();
        ButtonWidget z0Down = ButtonWidget.builder(Text.of("Pivot Z Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z0 = MathUtil.clamp(this.z0 - 16, -(maxSize/2), 0);
            }else {
                this.z0 = MathUtil.clamp(this.z0 - 1, -(maxSize/2), 0);
            }
            z0.setMessage(Text.of(String.valueOf(this.z0)));
        }).dimensions(200, 80, width, height).build();
        this.addDrawableChild(z0);
        this.addDrawableChild(z0Up);
        this.addDrawableChild(z0Down);

        TextWidget x1 = new TextWidget(Text.of(String.valueOf(this.x1)), this.client.textRenderer);
        x1.setDimensionsAndPosition(width, height, 40, 120);
        ButtonWidget x1Up = ButtonWidget.builder(Text.of("Size X up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x1 = MathUtil.clamp(this.x1 + 16, 0, maxSize+this.x0);
            }else {
                this.x1 = MathUtil.clamp(this.x1 + 1, 0, maxSize+this.x0);
            }
            x1.setMessage(Text.of(String.valueOf(this.x1)));
        }).dimensions(40, 140, width, height).build();
        ButtonWidget x1Down = ButtonWidget.builder(Text.of("Size X Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x1 = MathUtil.clamp(this.x1 - 16, 0, maxSize+this.x0);
            }else {
                this.x1 = MathUtil.clamp(this.x1 - 1, 0, maxSize+this.x0);
            }
            x1.setMessage(Text.of(String.valueOf(this.x1)));
        }).dimensions(40, 160, width, height).build();
        this.addDrawableChild(x1);
        this.addDrawableChild(x1Up);
        this.addDrawableChild(x1Down);

        TextWidget y1 = new TextWidget(Text.of(String.valueOf(this.y1)), this.client.textRenderer);
        y1.setDimensionsAndPosition(width, height, 120, 120);
        ButtonWidget y1Up = ButtonWidget.builder(Text.of("Size Y up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y1 = MathUtil.clamp(this.y1 + 16, 0, maxSize+this.y0);
            }else {
                this.y1 = MathUtil.clamp(this.y1 + 1, 0, maxSize+this.y0);
            }
            y1.setMessage(Text.of(String.valueOf(this.y1)));
        }).dimensions(120, 140, width, height).build();
        ButtonWidget y1Down = ButtonWidget.builder(Text.of("Size Y Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y1 = MathUtil.clamp(this.y1 - 16, 0, maxSize+this.y0);
            }else {
                this.y1 = MathUtil.clamp(this.y1 - 1, 0, maxSize+this.y0);
            }
            y1.setMessage(Text.of(String.valueOf(this.y1)));
        }).dimensions(120, 160, width, height).build();
        this.addDrawableChild(y1);
        this.addDrawableChild(y1Up);
        this.addDrawableChild(y1Down);

        TextWidget z1 = new TextWidget(Text.of(String.valueOf(this.z1)), this.client.textRenderer);
        z1.setDimensionsAndPosition(width, height, 200, 120);
        ButtonWidget z1Up = ButtonWidget.builder(Text.of("Size Z up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z1 = MathUtil.clamp(this.z1 + 16, 0, maxSize+this.z0);
            }else {
                this.z1 = MathUtil.clamp(this.z1 + 1, 0, maxSize+this.z0);
            }
            z1.setMessage(Text.of(String.valueOf(this.z1)));
        }).dimensions(200, 140, width, height).build();
        ButtonWidget z1Down = ButtonWidget.builder(Text.of("Size Z Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z1 = MathUtil.clamp(this.z1 - 16, 0, maxSize+this.z0);
            }else {
                this.z1 = MathUtil.clamp(this.z1 - 1, 0, maxSize+this.z0);
            }
            z1.setMessage(Text.of(String.valueOf(this.z1)));
        }).dimensions(200, 160, width, height).build();
        this.addDrawableChild(z1);
        this.addDrawableChild(z1Up);
        this.addDrawableChild(z1Down);

        ButtonWidget toggle = ButtonWidget.builder(Text.of("Box: " + show), (btn) -> {
            this.show = !show;
            btn.setMessage(Text.of(Boolean.toString(show)));
        }).dimensions(40, this.height-40, width, height).build();
        this.addDrawableChild(toggle);

        ButtonWidget Update = ButtonWidget.builder(Text.of("Update"), (btn) -> {
            NbtCompound compound = buildCompound();
            compound.putString("action", "update");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.client.setScreen(null);
        }).dimensions(120, this.height-40, width, height).build();
        Update.setTooltip(Tooltip.of(Text.literal("Update Bounding Box")));
        this.addDrawableChild(Update);

        TextFieldWidget name = new TextFieldWidget(this.textRenderer, this.width-width-(width/2)-40, this.height-80, width+(width/2), height, Text.of(this.name));
        name.setText(this.name);
        name.setTooltip(Tooltip.of(Text.literal("Set name")));
        name.setChangedListener((input)-> this.name = input);
        this.addDrawableChild(name);

        ButtonWidget save = ButtonWidget.builder(Text.of("Save"), (btn) -> {
            NbtCompound compound = buildCompound();
            compound.putString("action", "save");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.client.setScreen(null);
        }).dimensions(this.width-width-40, this.height-40, width, height).build();
        save.setTooltip(Tooltip.of(Text.literal("Save structure")));
        this.addDrawableChild(save);

        ButtonWidget load = ButtonWidget.builder(Text.of("load"), (btn) -> {
            NbtCompound compound = buildCompound();
            compound.putString("action", "load");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.client.setScreen(null);
        }).dimensions(this.width-width-width-40, this.height-40, width, height).build();
        load.setTooltip(Tooltip.of(Text.literal("Only works with registered structures, not generated!")));
        this.addDrawableChild(load);

        ButtonWidget glassFloor = ButtonWidget.builder(Text.of("Build floor"), (btn) -> {
            NbtCompound compound = buildCompound();
            compound.putString("action", "floor");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.client.setScreen(null);
        }).dimensions(this.width-width, 40, width, height).build();
        this.addDrawableChild(glassFloor);

    }

    private NbtCompound buildCompound(){
        NbtCompound compound = new NbtCompound();
        compound.putInt("posx", posx);
        compound.putInt("posy", posy);
        compound.putInt("posz", posz);
        compound.putString("name", name);
        compound.putBoolean("show", show);
        compound.putInt("x0", this.x0);
        compound.putInt("y0", this.y0);
        compound.putInt("z0", this.z0);
        compound.putInt("x1", this.x1);
        compound.putInt("y1", this.y1);
        compound.putInt("z1", this.z1);
        return compound;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context,mouseX,mouseY,delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        return super.mouseClicked(click, doubled);
    }

}