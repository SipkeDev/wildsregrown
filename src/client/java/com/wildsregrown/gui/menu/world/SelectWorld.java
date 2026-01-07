package com.wildsregrown.gui.menu.world;

import com.wildsregrown.gui.menu.builder.widgets.ButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.storage.LevelSummary;
import org.jetbrains.annotations.Nullable;

import static com.wildsregrown.WildsRegrown.modid;

@Environment(EnvType.CLIENT)
public class SelectWorld extends Screen {

    private final Identifier background = Identifier.of(modid, "gen_world_type");
    protected final Screen parent;
    private ButtonWidget deleteButton;
    private ButtonWidget selectButton;
    private ButtonWidget previewButton;
    private ButtonWidget editButton;
    private WorldsWidget levelList;
    private String selected;

    public SelectWorld(Screen parent) {
        super(Text.of(""));
        this.parent = parent;
        this.selected = "";
    }

    protected void init() {

        this.levelList = this.addDrawableChild(new WorldsWidget(this, this.client));

        int y = height/5, m = 22, dx = -124;

        this.selectButton = this.addDrawableChild(ButtonWidget.builder(Text.of("Load"), (button) -> {
            this.levelList.getSelectedAsOptional().ifPresent(WorldsWidget.LevelEntry::play);
        }).dimensions(this.width + dx, y+=m, 100, 20).build());

        this.previewButton = this.addDrawableChild(ButtonWidget.builder(Text.of("Preview"), (button) -> {
            this.levelList.getSelectedAsOptional().ifPresent(WorldsWidget.LevelEntry::preview);
        }).dimensions(this.width + dx, y+=m, 100, 20).build());

        this.editButton = this.addDrawableChild(ButtonWidget.builder(Text.of("Edit"),
                (button) -> this.levelList.getSelectedAsOptional().ifPresent(WorldsWidget.LevelEntry::edit)).dimensions(this.width + dx, y+=m, 100, 20).build());

        this.deleteButton = this.addDrawableChild(ButtonWidget.builder(Text.of("Delete"),
                (button) -> this.levelList.getSelectedAsOptional().ifPresent(WorldsWidget.LevelEntry::deleteIfConfirmed)).dimensions(this.width + dx, y+=m, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.BACK, (button) -> this.client.setScreen(this.parent)).dimensions(this.width + dx, y+=m, 100, 20).build());

        this.worldSelected(null);
    }

    protected void setInitialFocus() {
        this.setInitialFocus(this.levelList);
    }

    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.selected, this.width-72, 44, 16777215);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, width, height);
    }

    public void worldSelected(@Nullable LevelSummary level) {
        if (level == null) {
            this.selected = "";
            this.selectButton.active = false;
            this.previewButton.active = false;
            this.editButton.active = false;
            this.deleteButton.active = false;
        } else {
            this.selected = level.getDisplayName();
            this.selectButton.active = level.isSelectable();
            this.previewButton.active = true;
            this.editButton.active = level.isEditable();
            this.deleteButton.active = level.isDeletable();
        }

    }

}

