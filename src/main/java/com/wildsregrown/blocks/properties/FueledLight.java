package com.wildsregrown.blocks.properties;


import net.minecraft.util.StringIdentifiable;

public enum FueledLight implements StringIdentifiable {

    EMPTY("empty"),
    FILLED("filled"),
    LIT("lit");

    private final String name;

    private FueledLight(String name) {
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
