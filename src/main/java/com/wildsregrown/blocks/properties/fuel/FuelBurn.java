package com.wildsregrown.blocks.properties.fuel;


import net.minecraft.util.StringIdentifiable;

public enum FuelBurn implements StringIdentifiable {

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
    public String asString() {
        return this.name;
    }
}
