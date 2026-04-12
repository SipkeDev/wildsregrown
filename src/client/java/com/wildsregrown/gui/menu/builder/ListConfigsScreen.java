package com.wildsregrown.gui.menu.builder;

import com.sipke.api.grid.GridLoader;
import com.sipke.api.grid.WRGConfig;
import com.sipke.builder.WorldBuilder;
import com.sipke.core.list.Pair;
import com.wildsregrown.WildsRegrown;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

import java.util.Objects;

public class ListConfigsScreen extends Screen {

    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private final ConfigListWidget list;
    private final WorldBuilder builder;
    private final Screen parent;

    public ListConfigsScreen(WorldBuilder builder, Screen parent){
        super(Text.literal("config"));
        this.list = new ConfigListWidget(client);
        this.builder = builder;
        this.parent = parent;
    }

    @Override
    protected void init() {

        this.layout.setHeaderHeight(22);
        this.layout.addHeader(new TextWidget(Text.of("Builder Configs"), textRenderer));
        this.layout.addBody(list);
        initFooter();

        this.layout.forEachChild((child) -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });

        this.layout.refreshPositions();
        if (this.list != null) {
            this.list.position(this.width, this.layout);
        }

        for (ConfigListWidget.ConfigEntry entry : list.children()){
            WildsRegrown.LOGGER.info("Hello: " + entry.name);
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    protected void initFooter() {
        DirectionalLayoutWidget directionalLayoutWidget = DirectionalLayoutWidget.vertical().spacing(8);
        directionalLayoutWidget.getMainPositioner().alignHorizontalCenter();
        directionalLayoutWidget.add(new TextWidget(Text.of("Load config for the builder"), this.textRenderer));
        DirectionalLayoutWidget directionalLayoutWidget2 = directionalLayoutWidget.add(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.of("Load"), button -> {
            ConfigListWidget.ConfigEntry entry = list.getSelectedOrNull();
            if (entry != null) {
                onLoad(entry.config, entry.id);
            }
        }).build());
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.of("Cancel"), button -> {
            client.setScreen(parent);
        }).build());
        this.layout.setFooterHeight(55);
        this.layout.addFooter(directionalLayoutWidget);
    }

    private void onLoad(WRGConfig.Builder config, Text id) {
        builder.ctx.setConfig(config);
        SystemToast toast = SystemToast.create(client, SystemToast.Type.PERIODIC_NOTIFICATION, Text.of("Loaded config"), id);
        client.getToastManager().add(toast);
        client.setScreen(new ConfigScreen(builder, ((ConfigScreen)parent).getParent()));
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ConfigScreen.TAB_HEADER_BACKGROUND_TEXTURE, 0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.renderDarkening(context, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    @Environment(EnvType.CLIENT)
    class ConfigListWidget extends AlwaysSelectedEntryListWidget<ConfigListWidget.ConfigEntry> {

        public ConfigListWidget(final MinecraftClient client) {
            super(client, ListConfigsScreen.this.width, ListConfigsScreen.this.height - 33 - 53, 33, 18);
            for (Pair<WRGConfig.Builder, String> entry : GridLoader.listConfigs()){
                this.addEntry(new ConfigEntry(entry.v2, entry.v1));
            }
            this.setSelected(children().getFirst());
            if (this.getSelectedOrNull() != null) {
                this.centerScrollOn(this.getSelectedOrNull());
            }
        }

        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        @Environment(EnvType.CLIENT)
        public class ConfigEntry extends Entry<ConfigEntry> {

            final String name;
            private final Text id;
            private final WRGConfig.Builder config;

            public ConfigEntry(final String name, final WRGConfig.Builder config) {
                this.name = name;
                this.id = Text.of(name);
                this.config = config;
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
                TextRenderer textRender = ListConfigsScreen.this.textRenderer;
                int w = ListConfigsScreen.this.width / 2;
                int h = this.getContentMiddleY();
                //WRGVanilla.LOGGER.info(name);
                Objects.requireNonNull(ListConfigsScreen.this.textRenderer);
                context.drawCenteredTextWithShadow(textRender, id, w, h - 9 / 2, -1);
            }

            @Override
            public boolean keyPressed(KeyInput input) {
                if (input.isEnterOrSpace()) {
                    this.onPressed();
                    ListConfigsScreen.this.onLoad(config, id);
                    return true;
                } else {
                    return super.keyPressed(input);
                }
            }

            @Override
            public Text getNarration() {
                return Text.empty();
            }

            @Override
            public boolean mouseClicked(Click click, boolean doubled) {
                this.onPressed();
                if (doubled) {
                    ListConfigsScreen.this.onLoad(config, id);
                }
                return super.mouseClicked(click, doubled);
            }

            private void onPressed() {
               ConfigListWidget.this.setSelected(this);
            }

        }
    }

}
