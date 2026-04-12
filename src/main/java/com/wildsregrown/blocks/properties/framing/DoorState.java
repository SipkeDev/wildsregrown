package com.wildsregrown.blocks.properties.framing;

import net.minecraft.util.StringIdentifiable;

public enum DoorState implements StringIdentifiable {

    left_top("left_top"),
    left_bottom("left_bottom"),
    right_top("right_top"),
    right_bottom("right_bottom");

    private final String name;

    private DoorState(String name){
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static boolean isUpper(DoorState state){
        return state == left_top || state == right_top;
    }

    public static boolean isLeft(DoorState state){
        return state == left_bottom || state == left_top;
    }

}
