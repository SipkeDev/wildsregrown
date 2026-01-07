package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.util.StringIdentifiable;

public enum HorizontalConnected implements StringIdentifiable {

    SINGLE("single"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private HorizontalConnected(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}