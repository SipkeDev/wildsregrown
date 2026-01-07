package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.util.StringIdentifiable;

public enum CornerConnecting implements StringIdentifiable {

    NONE("none"),
    RIGHT_CORNER("corner"),
    RIGHT_NOOK("nook");

    private final String name;

    CornerConnecting(String name) {
        this.name = name;
    }


    public static CornerConnecting addSegment(CornerConnecting oldShape, CornerConnecting newShape) {
    return null;
    }




    @Override
    public String asString() {
        return this.name;
    }
}