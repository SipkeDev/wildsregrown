package com.wildsregrown.blocks.properties.framing;


import net.minecraft.util.StringRepresentable;

public enum Tudor implements StringRepresentable {

    hollow("hollow"),
    l_left("l_left"),
    l_right("l_right"),
    cross("cross"),
    bracketed("bracketed"),
    curved("curved");

    private final String name;

    Tudor(String name) {
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
