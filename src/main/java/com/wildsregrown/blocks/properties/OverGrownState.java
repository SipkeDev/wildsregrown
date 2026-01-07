package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

/**
 * Tells the game which state overgrown for the soil is.
 * clear/grass/forest/
 */
public enum OverGrownState implements StringIdentifiable {

    CLEAR("clear"),
    grass("grass"),
    forest("forest")
    ;

    private final String name;

    OverGrownState(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
