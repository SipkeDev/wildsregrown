package com.wildsregrown.blocks.properties;


import net.minecraft.util.StringIdentifiable;

public enum HalfStair implements StringIdentifiable {

    TOP("top"),
    BOTTOM("bottom"),
    TOP_INVERT("top_invert"),
    BOTTOM_INVERT("bottom_invert");

    private final String name;

    private HalfStair(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
