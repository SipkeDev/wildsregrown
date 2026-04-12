package com.wildsregrown.registries.world;

import com.sipke.registeries.WorldRegistries;
import com.sipke.registeries.core.RegistryObject;
import com.sipke.api.geology.GeoMaterial;
import com.wildsregrown.WildsRegrown;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MaterialRegistery {

    public static final RegistryObject<GeoMaterial> air = WorldRegistries.MATERIALS.register(new GeoMaterial("air", -1, 0.25f, 0.6f, 1.25f, 0, 0, 0));

    //misc
    public static final RegistryObject<GeoMaterial> snow = WorldRegistries.MATERIALS.register(new GeoMaterial("snow", Color.white.getRGB(), 0.125f, 0.9f, 0.25f, current(), current(), current()));

    //ores
    public static final RegistryObject<GeoMaterial> coal = WorldRegistries.MATERIALS.register(new GeoMaterial("coal", 0, 0.25f, 0.6f, 1.25f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> anthracite = WorldRegistries.MATERIALS.register(new GeoMaterial("anthracite", 0, 0.25f, 0.6f, 1.25f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> lignite = WorldRegistries.MATERIALS.register(new GeoMaterial("lignite", 0, 0.25f, 0.6f, 1.25f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> banded_iron = WorldRegistries.MATERIALS.register(new GeoMaterial("banded_iron", 0, 0.25f, 0.6f, 1.25f, current(), current(), current()));

    /**
     * Soils
     * - Sand
     * - Loam
     * - Clay
     * - Peat
     */
    
    public static final RegistryObject<GeoMaterial> sand_white = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_white", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_black = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_black", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_grey", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_brown", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_beige", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_yellow = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_yellow", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_pink = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_pink", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> sand_red = WorldRegistries.MATERIALS.register(new GeoMaterial("sand_red", 0, 0.48f, 0.85f, 0.125f, current(), current(), current()));

    public static final RegistryObject<GeoMaterial> loam_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("loam_beige", 0, 0.28f, 0.8f, 0.15f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> loam_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("loam_brown", 0, 0.28f, 0.8f, 0.15f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> loam_black = WorldRegistries.MATERIALS.register(new GeoMaterial("loam_black", 0, 0.28f, 0.8f, 0.15f, current(), current(), current()));

    public static final RegistryObject<GeoMaterial> clay_yellow = WorldRegistries.MATERIALS.register(new GeoMaterial("clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> clay_red = WorldRegistries.MATERIALS.register(new GeoMaterial("clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> clay_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("clay_beige", 0, 0.32f, 0.9f, 0.175f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> clay_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("clay_brown", 0, 0.32f, 0.9f, 0.175f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> clay_black = WorldRegistries.MATERIALS.register(new GeoMaterial("clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current()));

    public static final RegistryObject<GeoMaterial> peat_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("peat_brown", 0, 0.35f, 0.85f, 0.275f, current(), current(), current()));
    public static final RegistryObject<GeoMaterial> peat_black = WorldRegistries.MATERIALS.register(new GeoMaterial("peat_black", 0, 0.35f, 0.85f, 0.275f, current(), current(), current()));


    /**
     * Gravel
     */

    public static final RegistryObject<GeoMaterial> gravel_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_brown",     0, 0.32f, 0.92f, 0.25f, sand_brown.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_beige",     0, 0.32f, 0.92f, 0.25f, sand_beige.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_grey",       0, 0.32f, 0.92f, 0.25f, sand_grey.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_pink = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_pink",       0, 0.32f, 0.92f, 0.25f, sand_pink.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_red = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_red",         0, 0.32f, 0.92f, 0.25f, sand_red.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_yellow = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_yellow",   0, 0.32f, 0.92f, 0.25f, sand_yellow.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_blue = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_blue",       0, 0.32f, 0.92f, 0.25f, sand_beige.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_purple = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_purple",   0, 0.32f, 0.92f, 0.25f, sand_beige.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_black = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_black",     0, 0.32f, 0.92f, 0.25f, sand_black.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_white = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_white",     0, 0.32f, 0.92f, 0.25f, sand_white.getKey(), current(), current()));
    public static final RegistryObject<GeoMaterial> gravel_green = WorldRegistries.MATERIALS.register(new GeoMaterial("gravel_green",     0, 0.32f, 0.92f, 0.25f, sand_beige.getKey(), current(), current()));
    
    /**
     * Sedimentary Stone
     */
    public static final RegistryObject<GeoMaterial> sandstone_white_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_white_cobble", 0, 0.45f, 0.6f, 1.5f, sand_white.getKey(), gravel_white.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_white = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_white",               0, 0.7f, 0.7f, 5f, sand_white.getKey(), gravel_white.getKey(), sandstone_white_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_black_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_black_cobble", 0, 0.45f, 0.6f, 1.5f, sand_black.getKey(), gravel_black.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_black = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_black",               0, 0.7f, 0.7f, 5f, sand_black.getKey(), gravel_black.getKey(), sandstone_black_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_grey_cobble",   0, 0.45f, 0.6f, 1.5f, sand_grey.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_grey",                 0, 0.7f, 0.7f, 5f, sand_grey.getKey(), gravel_grey.getKey(), sandstone_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_beige_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_beige_cobble", 0, 0.45f, 0.6f, 1.5f, sand_beige.getKey(), gravel_beige.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_beige",               0, 0.7f, 0.7f, 5f, sand_beige.getKey(), gravel_beige.getKey(), sandstone_beige_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_brown_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_brown_cobble", 0, 0.45f, 0.6f, 1.5f, sand_brown.getKey(), gravel_brown.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_brown = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_brown",               0, 0.7f, 0.7f, 5f, sand_brown.getKey(), gravel_brown.getKey(), sandstone_brown_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_yellow_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_yellow_cobble",0, 0.45f, 0.6f, 1.5f, sand_yellow.getKey(), gravel_yellow.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_yellow = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_yellow",             0, 0.7f, 0.7f, 5f, sand_yellow.getKey(), gravel_yellow.getKey(), sandstone_yellow_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_pink_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_pink_cobble",   0, 0.45f, 0.6f, 1.5f, sand_pink.getKey(), gravel_pink.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_pink = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_pink",                 0, 0.7f, 0.7f, 5f, sand_pink.getKey(), gravel_pink.getKey(), sandstone_pink_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> sandstone_red_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_red_cobble",     0, 0.45f, 0.6f, 1.5f, sand_red.getKey(), gravel_red.getKey(), current()));
    public static final RegistryObject<GeoMaterial> sandstone_red = WorldRegistries.MATERIALS.register(new GeoMaterial("sandstone_red",                   0, 0.7f, 0.7f, 5f, sand_red.getKey(), gravel_red.getKey(), sandstone_red_cobble.getKey()));

    public static final RegistryObject<GeoMaterial> limestone_dark_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_dark_grey_cobble", 0, 0.24f, 0.38f, 1f, sand_brown.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> limestone_dark_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_dark_grey", 0, 0.32f, 0.18f, 3.5f, sand_brown.getKey(), gravel_grey.getKey(), limestone_dark_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> limestone_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_grey_cobble", 0, 0.24f, 0.38f, 1f, sand_brown.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> limestone_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_grey", 0, 0.32f, 0.18f, 3.5f, sand_brown.getKey(), gravel_grey.getKey(), limestone_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> limestone_beige_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_beige_cobble", 0, 0.24f, 0.38f, 1f, sand_beige.getKey(), gravel_beige.getKey(), current()));
    public static final RegistryObject<GeoMaterial> limestone_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_beige", 0, 0.32f, 0.18f, 3.5f, sand_beige.getKey(), gravel_beige.getKey(), limestone_beige_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> limestone_white_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_white_cobble", 0, 0.24f, 0.38f, 1f, sand_white.getKey(), gravel_white.getKey(), current()));
    public static final RegistryObject<GeoMaterial> limestone_white = WorldRegistries.MATERIALS.register(new GeoMaterial("limestone_white", 0, 0.32f, 0.18f, 3.5f, sand_white.getKey(), gravel_white.getKey(), limestone_white_cobble.getKey()));

    public static final RegistryObject<GeoMaterial> travertine_white_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_white_cobble", 0, 0.18f, 0.38f, 1.75f, sand_white.getKey(), gravel_white.getKey(), current()));
    public static final RegistryObject<GeoMaterial> travertine_white = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_white", 0, 0.35f, 0.125f, 4f, sand_white.getKey(), gravel_white.getKey(), travertine_white_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> travertine_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_grey_cobble", 0, 0.18f, 0.38f, 1.75f, sand_grey.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> travertine_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_grey", 0, 0.35f, 0.125f, 4f, sand_grey.getKey(), gravel_grey.getKey(), travertine_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> travertine_beige_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_beige_cobble", 0, 0.18f, 0.38f, 1.75f, sand_beige.getKey(), gravel_beige.getKey(), current()));
    public static final RegistryObject<GeoMaterial> travertine_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("travertine_beige", 0, 0.35f, 0.125f, 4f, sand_beige.getKey(), gravel_beige.getKey(), travertine_beige_cobble.getKey()));

    public static final RegistryObject<GeoMaterial> shale_black_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_black_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> shale_black = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_black", 0, 0.76f, 0.48f, 4.5f, sand_black.getKey(), gravel_grey.getKey(), shale_black_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> shale_dark_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_dark_grey_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> shale_dark_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_dark_grey", 0, 0.76f, 0.48f, 4.5f, sand_black.getKey(), gravel_grey.getKey(), shale_dark_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> shale_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_grey_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> shale_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_grey", 0, 0.76f, 0.48f, 4.5f, sand_black.getKey(), gravel_grey.getKey(), shale_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> shale_red_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_red_cobble", 0, 0.58f, 0.65f, 0.5f, sand_red.getKey(), gravel_red.getKey(), current()));
    public static final RegistryObject<GeoMaterial> shale_red = WorldRegistries.MATERIALS.register(new GeoMaterial("shale_red", 0, 0.7f, 0.49f, 4.5f, sand_red.getKey(), gravel_red.getKey(), shale_red_cobble.getKey()));

    /**
     * Metamorphic Stone
     */
    public static final RegistryObject<GeoMaterial> slate_grey_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_grey_cobble", 0, 0.25f, 0.28f, 21f, clay_beige.getKey(), gravel_grey.getKey(), current()));
    public static final RegistryObject<GeoMaterial> slate_grey = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_grey", 0, 0.38f, 0.075f, 6f, clay_beige.getKey(), gravel_grey.getKey(), slate_grey_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> slate_blue_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_blue_cobble", 0, 0.25f, 0.28f, 2f, clay_brown.getKey(), gravel_blue.getKey(), current()));
    public static final RegistryObject<GeoMaterial> slate_blue = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_blue", 0, 0.38f, 0.075f, 6f, clay_brown.getKey(), gravel_blue.getKey(), slate_blue_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> slate_purple_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_purple_cobble", 0, 0.25f, 0.28f, 2f, clay_black.getKey(), gravel_purple.getKey(), current()));
    public static final RegistryObject<GeoMaterial> slate_purple = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_purple", 0, 0.38f, 0.075f, 6f, clay_black.getKey(), gravel_purple.getKey(), slate_purple_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> slate_red_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_red_cobble", 0, 0.25f, 0.28f, 2f, clay_red.getKey(), gravel_red.getKey(), current()));
    public static final RegistryObject<GeoMaterial> slate_red = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_red", 0, 0.38f, 0.075f, 6f, clay_red.getKey(), gravel_red.getKey(), slate_red_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> slate_green_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_green_cobble", 0, 0.25f, 0.28f, 2f, clay_yellow.getKey(), gravel_green.getKey(), current()));
    public static final RegistryObject<GeoMaterial> slate_green = WorldRegistries.MATERIALS.register(new GeoMaterial("slate_green", 0, 0.38f, 0.075f, 6f, clay_yellow.getKey(), gravel_green.getKey(), slate_green_cobble.getKey()));

    public static final RegistryObject<GeoMaterial> marble_white_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_white_cobble", 0, 0.175f, 0.375f, 2f, sand_white.getKey(), gravel_white.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_white = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_white", 0, 0.38f, 0.175f, 2.5f, sand_white.getKey(), gravel_white.getKey(), marble_white_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> marble_beige_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_beige_cobble", 0, 0.175f, 0.375f, 2f, sand_beige.getKey(), gravel_beige.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_beige = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_beige", 0, 0.38f, 0.175f, 2.5f, sand_beige.getKey(), gravel_beige.getKey(), marble_beige_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> marble_black_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_black_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getKey(), gravel_black.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_black = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_black", 0, 0.38f, 0.175f, 2.5f, sand_black.getKey(), gravel_black.getKey(), marble_black_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> marble_portoro_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_portoro_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getKey(), gravel_black.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_portoro = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_portoro", 0, 0.38f, 0.175f, 2.5f, sand_black.getKey(), gravel_black.getKey(), marble_portoro_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> marble_green_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_green_cobble", 0, 0.175f, 0.375f, 2f, sand_beige.getKey(), gravel_green.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_green = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_green", 0, 0.38f, 0.175f, 2.5f, sand_beige.getKey(), gravel_green.getKey(), marble_green_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> marble_blue_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_blue_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getKey(), gravel_blue.getKey(), current()));
    public static final RegistryObject<GeoMaterial> marble_blue = WorldRegistries.MATERIALS.register(new GeoMaterial("marble_blue", 0, 0.38f, 0.175f, 2.5f, sand_black.getKey(), gravel_blue.getKey(), marble_blue_cobble.getKey()));


    /**
     * igneous Stone
     */
    public static final RegistryObject<GeoMaterial> granite_red_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_red_cobble", 0, 0.65f, 0.85f, 4f, sand_red.getKey(), gravel_red.getKey(), current()));
    public static final RegistryObject<GeoMaterial> granite_red = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_red", 0, 0.85f, 0.65f, 24f, sand_red.getKey(), gravel_red.getKey(), granite_red_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> granite_pink_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_pink_cobble", 0, 0.65f, 0.85f, 4f, sand_pink.getKey(), gravel_pink.getKey(), current()));
    public static final RegistryObject<GeoMaterial> granite_pink = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_pink", 0, 0.85f, 0.65f, 24f, sand_pink.getKey(), gravel_pink.getKey(), granite_pink_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> granite_white_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_white_cobble", 0, 0.65f, 0.85f, 4f, sand_white.getKey(), gravel_white.getKey(), current()));
    public static final RegistryObject<GeoMaterial> granite_white = WorldRegistries.MATERIALS.register(new GeoMaterial("granite_white", 0, 0.85f, 0.65f, 24f, sand_white.getKey(), gravel_white.getKey(), granite_white_cobble.getKey()));
    public static final RegistryObject<GeoMaterial> basalt_black_cobble = WorldRegistries.MATERIALS.register(new GeoMaterial("basalt_black_cobble", 0, 0.85f, 0.375f, 3f, sand_black.getKey(), gravel_black.getKey(), current()));
    public static final RegistryObject<GeoMaterial> basalt_black = WorldRegistries.MATERIALS.register(new GeoMaterial("basalt_black", 0, 0.98f, 0.125f, 30f, sand_black.getKey(), gravel_black.getKey(), basalt_black_cobble.getKey()));

    private static int current(){
        return WorldRegistries.MATERIALS.getEntries().size();
    }

    public static void init(){
        WildsRegrown.LOGGER.info("Registered materials");
        WorldRegistries.MATERIALS.bootstrap();

        for (GeoMaterial object : WorldRegistries.MATERIALS.getEntries()){
            String n = object.name;
            if (n.contains("sand") || n.contains("loam") || n.contains("clay") || n.contains("peat")){
                WorldRegistries.MATERIALS.tagSoil(object);
            }
            if (n.contains("limestone") || n.contains("marble")){
                WorldRegistries.MATERIALS.tagSoluable(object);
            }
            if (n.contains("gravel")){
                WorldRegistries.MATERIALS.tagGravel(object);
            }
        }
    }

}
