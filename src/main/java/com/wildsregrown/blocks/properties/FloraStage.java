package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

public enum FloraStage implements StringIdentifiable {

    HEALTHY  ("healthy"  ),
    BUDS     ("buds"     ),
    FLOWERING("flowering"),
    FRUITS   ("fruits"   ),
    FUNGUS   ("fungus"   ),
    BACTERIA ("bacteria" ),
    INSECTS  ("insects"  ),
    DEATH    ("death"    );

    private final String name;

    FloraStage(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static boolean isSick(FloraStage stage){
        return stage == FUNGUS || stage == BACTERIA || stage == INSECTS;
    }

}
