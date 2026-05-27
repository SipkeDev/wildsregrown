package com.wildsregrown.blocks.properties.framing.beam;

import net.minecraft.util.StringRepresentable;

public enum SupportConnected implements StringRepresentable {

    WALL("wall"),
    BRACKET("bracket"),
    CEIL("ceiling");

    private final String name;

    SupportConnected(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
