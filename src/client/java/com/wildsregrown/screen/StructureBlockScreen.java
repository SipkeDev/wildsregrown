package com.wildsregrown.screen;

import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import static wildsregrown.api.client.gui.KeyBindings.hasKeyDown;
/*

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
        super(Component.nullToEmpty(payload.compound().getStringOr("name","new_world")));
        this.posx = payload.compound().getIntOr("posx",0);
        this.posy = payload.compound().getIntOr("posy",0);
        this.posz = payload.compound().getIntOr("posz",0);
        this.name = payload.compound().getStringOr("name", "new");
        this.show = payload.compound().getBooleanOr("show", false);
        this.x0 = payload.compound().getIntOr("x0",0);
        this.y0 = payload.compound().getIntOr("y0",0);
        this.z0 = payload.compound().getIntOr("z0",0);
        this.x1 = payload.compound().getIntOr("x1",0);
        this.y1 = payload.compound().getIntOr("y1",0);
        this.z1 = payload.compound().getIntOr("z1",0);

        this.x = this.width/2;
        this.y = this.height/2;
    }

    @Override
    protected void init() {
        int width = 80;
        int height = 20;

        StringWidget x0 = new StringWidget(Component.nullToEmpty(String.valueOf(this.x0)), this.minecraft.font);
        x0.setRectangle(width, height, 40, 40);
        Button x0Up = Button.builder(Component.nullToEmpty("Pivot X up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x0 = MathUtil.clamp(this.x0 + 16, -(maxSize/2), 0);
            }else {
                this.x0 = MathUtil.clamp(this.x0 + 1, -(maxSize/2), 0);
            }
            x0.setMessage(Component.nullToEmpty(String.valueOf(this.x0)));
        }).bounds(40, 60, width, height).build();
        Button x0Down = Button.builder(Component.nullToEmpty("Pivot X Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x0 = MathUtil.clamp(this.x0 - 16, -(maxSize/2), 0);
            }else {
                this.x0 = MathUtil.clamp(this.x0 - 1, -(maxSize/2), 0);
            }
            x0.setMessage(Component.nullToEmpty(String.valueOf(this.x0)));
        }).bounds(40, 80, width, height).build();
        this.addRenderableWidget(x0);
        this.addRenderableWidget(x0Up);
        this.addRenderableWidget(x0Down);

        StringWidget y0 = new StringWidget(Component.nullToEmpty(String.valueOf(this.y0)), this.minecraft.font);
        y0.setRectangle(width, height, 120, 40);
        Button y0Up = Button.builder(Component.nullToEmpty("Pivot Y up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y0 = MathUtil.clamp(this.y0 + 16, -(maxSize/2), 0);
            }else {
                this.y0 = MathUtil.clamp(this.y0 + 1, -(maxSize/2), 0);
            }
            y0.setMessage(Component.nullToEmpty(String.valueOf(this.y0)));
        }).bounds(120, 60, width, height).build();
        Button y0Down = Button.builder(Component.nullToEmpty("Pivot Y Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y0 = MathUtil.clamp(this.y0 - 16, -(maxSize/2), 0);
            }else {
                this.y0 = MathUtil.clamp(this.y0 - 1, -(maxSize/2), 0);
            }
            y0.setMessage(Component.nullToEmpty(String.valueOf(this.y0)));
        }).bounds(120, 80, width, height).build();
        this.addRenderableWidget(y0);
        this.addRenderableWidget(y0Up);
        this.addRenderableWidget(y0Down);

        StringWidget z0 = new StringWidget(Component.nullToEmpty(String.valueOf(this.z0)), this.minecraft.font);
        z0.setRectangle(width, height, 200, 40);
        Button z0Up = Button.builder(Component.nullToEmpty("Pivot Z up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z0 = MathUtil.clamp(this.z0 + 16, -(maxSize/2), 0);
            }else {
                this.z0 = MathUtil.clamp(this.z0 + 1, -(maxSize/2), 0);
            }
            z0.setMessage(Component.nullToEmpty(String.valueOf(this.z0)));
        }).bounds(200, 60, width, height).build();
        Button z0Down = Button.builder(Component.nullToEmpty("Pivot Z Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z0 = MathUtil.clamp(this.z0 - 16, -(maxSize/2), 0);
            }else {
                this.z0 = MathUtil.clamp(this.z0 - 1, -(maxSize/2), 0);
            }
            z0.setMessage(Component.nullToEmpty(String.valueOf(this.z0)));
        }).bounds(200, 80, width, height).build();
        this.addRenderableWidget(z0);
        this.addRenderableWidget(z0Up);
        this.addRenderableWidget(z0Down);

        StringWidget x1 = new StringWidget(Component.nullToEmpty(String.valueOf(this.x1)), this.minecraft.font);
        x1.setRectangle(width, height, 40, 120);
        Button x1Up = Button.builder(Component.nullToEmpty("Size X up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x1 = MathUtil.clamp(this.x1 + 16, 0, maxSize+this.x0);
            }else {
                this.x1 = MathUtil.clamp(this.x1 + 1, 0, maxSize+this.x0);
            }
            x1.setMessage(Component.nullToEmpty(String.valueOf(this.x1)));
        }).bounds(40, 140, width, height).build();
        Button x1Down = Button.builder(Component.nullToEmpty("Size X Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.x1 = MathUtil.clamp(this.x1 - 16, 0, maxSize+this.x0);
            }else {
                this.x1 = MathUtil.clamp(this.x1 - 1, 0, maxSize+this.x0);
            }
            x1.setMessage(Component.nullToEmpty(String.valueOf(this.x1)));
        }).bounds(40, 160, width, height).build();
        this.addRenderableWidget(x1);
        this.addRenderableWidget(x1Up);
        this.addRenderableWidget(x1Down);

        StringWidget y1 = new StringWidget(Component.nullToEmpty(String.valueOf(this.y1)), this.minecraft.font);
        y1.setRectangle(width, height, 120, 120);
        Button y1Up = Button.builder(Component.nullToEmpty("Size Y up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y1 = MathUtil.clamp(this.y1 + 16, 0, maxSize+this.y0);
            }else {
                this.y1 = MathUtil.clamp(this.y1 + 1, 0, maxSize+this.y0);
            }
            y1.setMessage(Component.nullToEmpty(String.valueOf(this.y1)));
        }).bounds(120, 140, width, height).build();
        Button y1Down = Button.builder(Component.nullToEmpty("Size Y Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.y1 = MathUtil.clamp(this.y1 - 16, 0, maxSize+this.y0);
            }else {
                this.y1 = MathUtil.clamp(this.y1 - 1, 0, maxSize+this.y0);
            }
            y1.setMessage(Component.nullToEmpty(String.valueOf(this.y1)));
        }).bounds(120, 160, width, height).build();
        this.addRenderableWidget(y1);
        this.addRenderableWidget(y1Up);
        this.addRenderableWidget(y1Down);

        StringWidget z1 = new StringWidget(Component.nullToEmpty(String.valueOf(this.z1)), this.minecraft.font);
        z1.setRectangle(width, height, 200, 120);
        Button z1Up = Button.builder(Component.nullToEmpty("Size Z up"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z1 = MathUtil.clamp(this.z1 + 16, 0, maxSize+this.z0);
            }else {
                this.z1 = MathUtil.clamp(this.z1 + 1, 0, maxSize+this.z0);
            }
            z1.setMessage(Component.nullToEmpty(String.valueOf(this.z1)));
        }).bounds(200, 140, width, height).build();
        Button z1Down = Button.builder(Component.nullToEmpty("Size Z Down"), (btn) -> {
            if (hasKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
                this.z1 = MathUtil.clamp(this.z1 - 16, 0, maxSize+this.z0);
            }else {
                this.z1 = MathUtil.clamp(this.z1 - 1, 0, maxSize+this.z0);
            }
            z1.setMessage(Component.nullToEmpty(String.valueOf(this.z1)));
        }).bounds(200, 160, width, height).build();
        this.addRenderableWidget(z1);
        this.addRenderableWidget(z1Up);
        this.addRenderableWidget(z1Down);

        Button toggle = Button.builder(Component.nullToEmpty("Box: " + show), (btn) -> {
            this.show = !show;
            btn.setMessage(Component.nullToEmpty(Boolean.toString(show)));
        }).bounds(40, this.height-40, width, height).build();
        this.addRenderableWidget(toggle);

        Button Update = Button.builder(Component.nullToEmpty("Update"), (btn) -> {
            CompoundTag compound = buildCompound();
            compound.putString("action", "update");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.minecraft.setScreen(null);
        }).bounds(120, this.height-40, width, height).build();
        Update.setTooltip(Tooltip.create(Component.literal("Update Bounding Box")));
        this.addRenderableWidget(Update);

        EditBox name = new EditBox(this.font, this.width-width-(width/2)-40, this.height-80, width+(width/2), height, Component.nullToEmpty(this.name));
        name.setValue(this.name);
        name.setTooltip(Tooltip.create(Component.literal("Set name")));
        name.setResponder((input)-> this.name = input);
        this.addRenderableWidget(name);

        Button save = Button.builder(Component.nullToEmpty("Save"), (btn) -> {
            CompoundTag compound = buildCompound();
            compound.putString("action", "save");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.minecraft.setScreen(null);
        }).bounds(this.width-width-40, this.height-40, width, height).build();
        save.setTooltip(Tooltip.create(Component.literal("Save structure")));
        this.addRenderableWidget(save);

        Button load = Button.builder(Component.nullToEmpty("load"), (btn) -> {
            CompoundTag compound = buildCompound();
            compound.putString("action", "load");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.minecraft.setScreen(null);
        }).bounds(this.width-width-width-40, this.height-40, width, height).build();
        load.setTooltip(Tooltip.create(Component.literal("Only works with registered structures, not generated!")));
        this.addRenderableWidget(load);

        Button glassFloor = Button.builder(Component.nullToEmpty("Build floor"), (btn) -> {
            CompoundTag compound = buildCompound();
            compound.putString("action", "floor");
            ClientPlayNetworking.send(new StructureBlockPayload(compound));
            this.minecraft.setScreen(null);
        }).bounds(this.width-width, 40, width, height).build();
        this.addRenderableWidget(glassFloor);

    }

    private CompoundTag buildCompound(){
        CompoundTag compound = new CompoundTag();
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
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context,mouseX,mouseY,delta);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        return super.mouseClicked(click, doubled);
    }

}

 */