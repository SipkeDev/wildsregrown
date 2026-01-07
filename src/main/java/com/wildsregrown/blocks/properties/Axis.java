package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

public enum Axis implements StringIdentifiable {
    NEGATIVE("negative"),
    POSITIVE("positive"),
    NONE("none");

    private final String name;

    private Axis(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
    public Axis flip() {
        switch (this) {
            case NEGATIVE -> {return POSITIVE;}
            case POSITIVE -> {return NEGATIVE;}
            default -> {return NONE;}
        }
    }
}