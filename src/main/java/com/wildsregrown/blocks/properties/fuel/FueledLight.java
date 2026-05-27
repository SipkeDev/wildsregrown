package com.wildsregrown.blocks.properties.fuel;


import net.minecraft.util.StringRepresentable;

public enum FueledLight implements StringRepresentable {

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
    public String getSerializedName() {
        return this.name;
    }
}
