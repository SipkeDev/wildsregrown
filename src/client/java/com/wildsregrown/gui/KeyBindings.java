package com.wildsregrown.gui;

import com.wildsregrown.gui.map.WorldMapScreen;
import com.wildsregrown.gui.radial.RadialScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static int TAB = GLFW.GLFW_KEY_TAB;
    public static int M = GLFW.GLFW_KEY_M;

    public static KeyBinding MENU_OPEN = new KeyBinding("key.open_menu", TAB, KeyBinding.Category.MISC);
    public static KeyBinding MAP_OPEN = new KeyBinding("key.open_map", M, KeyBinding.Category.MISC);

    public static void register() {

        //Keybinds Registery
        KeyBindingHelper.registerKeyBinding(MENU_OPEN);
        KeyBindingHelper.registerKeyBinding(MAP_OPEN);
    }

    public static void handleKeybinds(){
        //Handle Keybinds
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (hasKeyDown(TAB)){
                if (!RadialScreen.active) {
                    RadialScreen.show();
                }
            }else {
                if (RadialScreen.active) {
                    RadialScreen.hide();
                }
            }

            if (MAP_OPEN.wasPressed()) {
                if (WorldMapScreen.active){
                    WorldMapScreen.hide();
                }
                if (!WorldMapScreen.active) {
                    WorldMapScreen.show();
                }
            }

        });
    }

    public static boolean hasKeyDown(KeyInput input){
        return hasKeyDown(input.getKeycode());
    }
    public static boolean hasKeyDown(int code) {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow(), code);
    }

}
