package com.wildsregrown.gui.menu.main;

import com.sipke.api.features.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;

import static com.wildsregrown.WildsRegrown.modid;

public class MainButtonWidget extends AbstractButton {

    private static final WidgetSprites textures = new WidgetSprites(Identifier.fromNamespaceAndPath(modid, "widget/wood_button"), Identifier.fromNamespaceAndPath(modid, "widget/wood_button_disabled"), Identifier.fromNamespaceAndPath(modid, "widget/wood_button_highlighted"));
    protected final MainButtonWidget.PressAction onPress;

    public static MainButtonWidget.Builder builder(Component message, PressAction onPress) {
        return new MainButtonWidget.Builder(message, onPress);
    }

    protected MainButtonWidget(int x, int y, int width, int height, Component message, PressAction onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.onPress.onPress(this);
    }

    @Override
    protected void renderContents(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        Minecraft minecraftClient = Minecraft.getInstance();
        context.blitSprite(RenderPipelines.GUI_TEXTURED, textures.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        this.renderDefaultLabel(context.textRendererForWidget(this, GuiGraphics.HoveredTextEffects.NONE));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {}

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Component message;
        private final PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Component message, PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public MainButtonWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public MainButtonWidget.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public MainButtonWidget.Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public MainButtonWidget.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public MainButtonWidget build() {
            MainButtonWidget buttonWidget = new MainButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress);
            buttonWidget.setTooltip(this.tooltip);
            return buttonWidget;
        }
    }

    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(MainButtonWidget button);
    }

}
