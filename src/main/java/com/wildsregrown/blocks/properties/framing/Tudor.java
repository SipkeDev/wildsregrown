package com.wildsregrown.blocks.properties.framing;


import net.minecraft.util.StringIdentifiable;

public enum Tudor implements StringIdentifiable {

    hollow("hollow"),
    l_left("l_left"),
    l_right("l_right"),
    cross("cross"),
    bracketed("bracketed"),
    curved("curved");

    private final String name;

    private Tudor(String name) {
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
