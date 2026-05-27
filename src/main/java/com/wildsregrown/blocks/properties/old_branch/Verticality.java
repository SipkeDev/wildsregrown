package com.wildsregrown.blocks.properties.old_branch;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public enum Verticality implements StringRepresentable {

    UP("up"),
    DOWN("down"),
    LEVEL("level");

    private final String name;
    public static final EnumProperty<Verticality> VERTICALITY = EnumProperty.create("verticality", Verticality.class);

    private Verticality(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}