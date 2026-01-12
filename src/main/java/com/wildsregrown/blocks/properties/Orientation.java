package com.wildsregrown.blocks.properties;


import net.minecraft.util.StringIdentifiable;

public enum Orientation implements StringIdentifiable {

    UP("up"),
    RIGHT("right"),
    DOWN("down"),
    LEFT("left");

    private final String name;

    Orientation(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
