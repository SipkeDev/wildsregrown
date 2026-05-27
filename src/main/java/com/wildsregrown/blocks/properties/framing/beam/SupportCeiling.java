package com.wildsregrown.blocks.properties.framing.beam;

import net.minecraft.util.StringRepresentable;

public enum SupportCeiling implements StringRepresentable {

    //Single indentity
    STRAIGHT("straight"),
    STRAIGHT_ROTATED("rotated"),
    T_JOINT_NORTH("t_north"),
    T_JOINT_EAST("t_east"),
    T_JOINT_SOUTH("t_south"),
    T_JOINT_WEST("t_west"),
    CROSS("cross");

    private final String name;

    private SupportCeiling(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}