package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.util.StringIdentifiable;

public enum VerticalConnected implements StringIdentifiable {

    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    VerticalConnected(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}