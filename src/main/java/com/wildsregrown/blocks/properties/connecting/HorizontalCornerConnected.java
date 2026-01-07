package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum HorizontalCornerConnected implements StringIdentifiable {
    SINGLE("single"),
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right");

    private final String name;
    public static final EnumProperty<HorizontalCornerConnected> PART = EnumProperty.of("part", HorizontalCornerConnected.class);

    private HorizontalCornerConnected(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}