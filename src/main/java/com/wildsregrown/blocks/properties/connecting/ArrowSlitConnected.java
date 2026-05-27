package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.util.StringRepresentable;

public enum ArrowSlitConnected implements StringRepresentable {

    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom"),
    BOTTOM_FLOOR("bottom_floor");

    private final String name;

    ArrowSlitConnected(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}