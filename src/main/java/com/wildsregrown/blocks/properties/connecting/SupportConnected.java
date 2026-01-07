package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.util.StringIdentifiable;

public enum SupportConnected implements StringIdentifiable {

    WALL("wall"),
    BRACKET("bracket"),
    CEIL("ceiling");

    private final String name;

    SupportConnected(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
