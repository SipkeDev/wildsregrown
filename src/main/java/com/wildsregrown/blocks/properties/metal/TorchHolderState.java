package com.wildsregrown.blocks.properties.metal;

import net.minecraft.util.StringRepresentable;

public enum TorchHolderState implements StringRepresentable {

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
    public String getSerializedName() {
        return this.name;
    }

}
