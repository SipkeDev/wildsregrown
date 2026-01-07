package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

@Deprecated
public enum FloralMoisture implements StringIdentifiable {

    DRY("dry"),
    NORMAL("normal"),
    WET("wet");

    private final String name;

    FloralMoisture(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
