package com.wildsregrown.blocks.properties;

import net.minecraft.util.StringIdentifiable;

/**
 * Tells the game which state the soil is in.
 * dry/temperate/wet * none/sparse/grass/forest
 * Somehow the idea is that a single array is better.
 */
@Deprecated
public enum SoilState implements StringIdentifiable {

    DRY("dry"),
    TEMPERATE("temperate"),
    WET("wet"),

    DRY_SPARSE_GRASS("dry_sparse_grass"),
    SPARSE_GRASS("sparse_grass"),
    WET_SPARSE_GRASS("wet_sparse_grass"),

    DRY_GRASS("dry_grass"),
    GRASS("grass"),
    WET_GRASS("wet_grass"),

    DRY_FOREST_FLOOR("dry_forest_floor"),
    FOREST_FLOOR("forest_floor"),
    WET_FOREST_FLOOR("wet_forest_floor")
    ;

    private final String name;

    SoilState(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static boolean isWet(SoilState state){
        return state == WET || state == WET_SPARSE_GRASS || state == WET_GRASS || state == WET_FOREST_FLOOR;
    }

    public static boolean isTemperate(SoilState state){
        return state == TEMPERATE || state == SPARSE_GRASS || state == GRASS || state == FOREST_FLOOR;
    }

    public static boolean isDry(SoilState state){
        return state == DRY || state == DRY_SPARSE_GRASS || state == DRY_GRASS || state == DRY_FOREST_FLOOR;
    }

}
