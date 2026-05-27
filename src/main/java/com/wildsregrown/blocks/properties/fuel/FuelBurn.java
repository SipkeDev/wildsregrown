package com.wildsregrown.blocks.properties.fuel;


import net.minecraft.util.StringRepresentable;

public enum FuelBurn implements StringRepresentable {

    off("off"),
    lit("lit"),
    starting("starting");

    private final String name;

    private FuelBurn(String name) {
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
