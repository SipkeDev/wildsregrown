package com.wildsregrown.blocks.properties;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum Verticality implements StringIdentifiable {

    UP("up"),
    DOWN("down"),
    LEVEL("level");

    private final String name;
    public static final EnumProperty<Verticality> VERTICALITY = EnumProperty.of("verticality", Verticality.class);

    private Verticality(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}