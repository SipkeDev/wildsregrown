package com.wildsregrown.network;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class Events {

    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
        });
    }

}
