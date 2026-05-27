package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringRepresentable;

public enum Quadrant implements StringRepresentable {

    I("i"),
    II("ii"),
    III("iii"),
    IV("iv");
    /*
    PP("pp"),
    NP("np"),
    NN("nn"),
    PN("pn");
     */

    private final String name;

    Quadrant(String name) {
        this.name = name;
    }

    public Quadrant mirrorH() {
        switch (this) {
            case IV -> {return III;}
            case III -> {return IV;}
            case II -> {return I;}
            default -> {return II;}
        }
    }

    public Quadrant rotateCC() {
        return this.ordinal() == 3 ? I : values()[this.ordinal()+1];
    }

    public Quadrant rotate() {
        return this.ordinal() == 0 ? IV : values()[this.ordinal()-1];
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}