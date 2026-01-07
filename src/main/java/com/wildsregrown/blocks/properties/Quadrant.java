package com.wildsregrown.blocks.properties;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum Quadrant implements StringIdentifiable {
    PP("pp"),
    NP("np"),
    NN("nn"),
    PN("pn");

    private final String name;
    public static final EnumProperty<Quadrant> QUADRANT = EnumProperty.of("quadrant", Quadrant.class);

    Quadrant(String name) {
        this.name = name;
    }

    public Quadrant mirrorH() {
        switch (this) {
            case PN -> {return NN;}
            case NN -> {return PN;}
            case NP -> {return PP;}
            default -> {return NP;}
        }
    }

    public Quadrant rotateCC() {
        return this.ordinal() == 3 ? PP : values()[this.ordinal()+1];
    }

    public Quadrant rotate() {
        return this.ordinal() == 0 ? PN : values()[this.ordinal()-1];
    }

    @Override
    public String asString() {
        return this.name;
    }
}