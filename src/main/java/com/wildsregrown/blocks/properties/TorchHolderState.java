package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

public enum TorchHolderState implements StringIdentifiable {

    EMPTY("empty"),
    UNLIT("unlit"),
    LIT("lit");

    private final String name;

    private TorchHolderState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
