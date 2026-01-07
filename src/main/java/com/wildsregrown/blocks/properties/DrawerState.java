package com.wildsregrown.blocks.properties;


import net.minecraft.util.StringIdentifiable;

public enum DrawerState implements StringIdentifiable {

    CLOSED("closed"),
    DRAWER_TOP("drawer_top"),
    DRAWER_BOTTOM("drawer_bottom"),
    OPEN("open");

    private final String name;

    DrawerState(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
