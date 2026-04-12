package com.wildsregrown.gui.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PlayerScreen extends Screen{

    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final PlayerScreen INSTANCE = new PlayerScreen();
    public static boolean active;

    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private final TabNavigationWidget tabNavigation;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);

    public TabNavigationWidget getTabNavigation() {
        return tabNavigation;
    }

    private PlayerScreen() {
        super(Text.of("wrg.inventory.title"));

        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width).tabs(
                new Tab[]{
                        new Inventory(textRenderer),
                        new WorldMap(textRenderer),
                        new Crafting(textRenderer)
                }).build();
        this.addDrawableChild(this.tabNavigation);

        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.of("Close"), (button) -> hide()).dimensions(0,0, 80, 20).build());

        this.layout.forEachChild((child) -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);

        this.refreshWidgetPositions();
    }

    public void refreshWidgetPositions() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int i = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - this.layout.getFooterHeight() - i);
            this.tabManager.setTabArea(screenRect);
            this.layout.setHeaderHeight(i);
            this.layout.refreshPositions();
        }
    }

    public static void show(){
        if (client.world != null) {
            if (!active && !client.inGameHud.getChatHud().isChatFocused()) {
                //Activate instance
                active = true;
                client.setScreen(INSTANCE);
            }
        }
    }

    public static void hide() {
        if (client.world != null) {
            if (active) {
                //Disable instance
                active = false;
                client.setScreen(null);
            }
        }
    }

    //behaviour
    @Override
    public boolean shouldPause() {
        return true;
    }

}