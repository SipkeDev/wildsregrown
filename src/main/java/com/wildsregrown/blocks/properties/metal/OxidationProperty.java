package com.wildsregrown.blocks.properties.metal;


import net.minecraft.util.StringRepresentable;

public enum OxidationProperty implements StringRepresentable {

    EMPTY("empty"),
    FILLED("filled"),
    LIT("lit");

    private final String name;

    private OxidationProperty(String name) {
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
