package com.wildsregrown.blocks.properties.connecting;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum ArchConnected implements StringIdentifiable {

    //Single indentity
    SINGLE("single"),

    //Double indentity
    DOUBLE_LEFT("left"),
    DOUBLE_RIGHT("right"),

    //Triple indentity
    TRIPLE_LEFT("triple_left"),
    TRIPLE_MIDDLE("triple_middle"),
    TRIPLE_RIGHT("triple_right"),

    //Quad identity
    QUAD_LEFT_LEFT("quad_left_left"),
    QUAD_LEFT_MIDDLE("quad_left_middle"),
    QUAD_RIGHT_MIDDLE("quad_right_middle"),
    QUAD_RIGHT_RIGHT("quad_right_right");

    private final String name;
    public static final EnumProperty<ArchConnected> ARCH = EnumProperty.of("arch", ArchConnected.class);

    private ArchConnected(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}