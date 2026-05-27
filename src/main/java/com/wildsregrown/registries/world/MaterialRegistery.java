package com.wildsregrown.registries.world;

import com.sipke.registeries.IndexedContainer;
import com.sipke.registeries.IndexedContainer;
import com.sipke.registeries.WorldRegistries;
import com.sipke.api.geology.GeoMaterial;
import com.wildsregrown.WildsRegrown;

import java.awt.*;

import static com.wildsregrown.WildsRegrown.modid;

public class MaterialRegistery {

    //misc
    public static final IndexedContainer<GeoMaterial> snow = WorldRegistries.MATERIALS.register(modid, "snow", Color.white.getRGB(), 0.125f, 0.9f, 0.25f, current(), current(), current());

    //ores
    public static final IndexedContainer<GeoMaterial> coal = WorldRegistries.MATERIALS.register(modid, "coal", 0, 0.25f, 0.6f, 1.25f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> anthracite = WorldRegistries.MATERIALS.register(modid, "anthracite", 0, 0.25f, 0.6f, 1.25f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> lignite = WorldRegistries.MATERIALS.register(modid, "lignite", 0, 0.25f, 0.6f, 1.25f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> banded_iron = WorldRegistries.MATERIALS.register(modid, "banded_iron", 0, 0.25f, 0.6f, 1.25f, current(), current(), current());

    /**
     * Soils
     * - Sand
     * - Loam
     * - Clay
     * - Peat
     */
    
    public static final IndexedContainer<GeoMaterial> sand_white = WorldRegistries.MATERIALS.register(modid, "sand_white", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_black = WorldRegistries.MATERIALS.register(modid, "sand_black", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_grey = WorldRegistries.MATERIALS.register(modid, "sand_grey", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_brown = WorldRegistries.MATERIALS.register(modid, "sand_brown", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_beige = WorldRegistries.MATERIALS.register(modid, "sand_beige", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_yellow = WorldRegistries.MATERIALS.register(modid, "sand_yellow", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_pink = WorldRegistries.MATERIALS.register(modid, "sand_pink", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> sand_red = WorldRegistries.MATERIALS.register(modid, "sand_red", 0, 0.48f, 0.85f, 0.125f, current(), current(), current());

    public static final IndexedContainer<GeoMaterial> loam_beige = WorldRegistries.MATERIALS.register(modid, "loam_beige", 0, 0.28f, 0.8f, 0.15f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> loam_brown = WorldRegistries.MATERIALS.register(modid, "loam_brown", 0, 0.28f, 0.8f, 0.15f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> loam_black = WorldRegistries.MATERIALS.register(modid, "loam_black", 0, 0.28f, 0.8f, 0.15f, current(), current(), current());

    public static final IndexedContainer<GeoMaterial> clay_yellow = WorldRegistries.MATERIALS.register(modid, "clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> clay_red = WorldRegistries.MATERIALS.register(modid, "clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> clay_beige = WorldRegistries.MATERIALS.register(modid, "clay_beige", 0, 0.32f, 0.9f, 0.175f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> clay_brown = WorldRegistries.MATERIALS.register(modid, "clay_brown", 0, 0.32f, 0.9f, 0.175f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> clay_black = WorldRegistries.MATERIALS.register(modid, "clay_black", 0, 0.32f, 0.95f, 0.175f, current(), current(), current());

    public static final IndexedContainer<GeoMaterial> peat_brown = WorldRegistries.MATERIALS.register(modid, "peat_brown", 0, 0.35f, 0.85f, 0.275f, current(), current(), current());
    public static final IndexedContainer<GeoMaterial> peat_black = WorldRegistries.MATERIALS.register(modid, "peat_black", 0, 0.35f, 0.85f, 0.275f, current(), current(), current());


    /**
     * Gravel
     */

    public static final IndexedContainer<GeoMaterial> gravel_brown = WorldRegistries.MATERIALS.register(modid, "gravel_brown",     0, 0.32f, 0.92f, 0.25f, sand_brown.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_beige = WorldRegistries.MATERIALS.register(modid, "gravel_beige",     0, 0.32f, 0.92f, 0.25f, sand_beige.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_grey = WorldRegistries.MATERIALS.register(modid, "gravel_grey",       0, 0.32f, 0.92f, 0.25f, sand_grey.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_pink = WorldRegistries.MATERIALS.register(modid, "gravel_pink",       0, 0.32f, 0.92f, 0.25f, sand_pink.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_red = WorldRegistries.MATERIALS.register(modid, "gravel_red",         0, 0.32f, 0.92f, 0.25f, sand_red.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_yellow = WorldRegistries.MATERIALS.register(modid, "gravel_yellow",   0, 0.32f, 0.92f, 0.25f, sand_yellow.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_blue = WorldRegistries.MATERIALS.register(modid, "gravel_blue",       0, 0.32f, 0.92f, 0.25f, sand_beige.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_purple = WorldRegistries.MATERIALS.register(modid, "gravel_purple",   0, 0.32f, 0.92f, 0.25f, sand_beige.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_black = WorldRegistries.MATERIALS.register(modid, "gravel_black",     0, 0.32f, 0.92f, 0.25f, sand_black.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_white = WorldRegistries.MATERIALS.register(modid, "gravel_white",     0, 0.32f, 0.92f, 0.25f, sand_white.getIndex(), current(), current());
    public static final IndexedContainer<GeoMaterial> gravel_green = WorldRegistries.MATERIALS.register(modid, "gravel_green",     0, 0.32f, 0.92f, 0.25f, sand_beige.getIndex(), current(), current());
    
    /**
     * Sedimentary Stone
     */
    public static final IndexedContainer<GeoMaterial> sandstone_white_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_white_cobble", 0, 0.45f, 0.6f, 1.5f, sand_white.getIndex(), gravel_white.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_white = WorldRegistries.MATERIALS.register(modid, "sandstone_white",               0, 0.7f, 0.7f, 5f, sand_white.getIndex(), gravel_white.getIndex(), sandstone_white_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_black_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_black_cobble", 0, 0.45f, 0.6f, 1.5f, sand_black.getIndex(), gravel_black.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_black = WorldRegistries.MATERIALS.register(modid, "sandstone_black",               0, 0.7f, 0.7f, 5f, sand_black.getIndex(), gravel_black.getIndex(), sandstone_black_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_grey_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_grey_cobble",   0, 0.45f, 0.6f, 1.5f, sand_grey.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_grey = WorldRegistries.MATERIALS.register(modid, "sandstone_grey",                 0, 0.7f, 0.7f, 5f, sand_grey.getIndex(), gravel_grey.getIndex(), sandstone_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_beige_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_beige_cobble", 0, 0.45f, 0.6f, 1.5f, sand_beige.getIndex(), gravel_beige.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_beige = WorldRegistries.MATERIALS.register(modid, "sandstone_beige",               0, 0.7f, 0.7f, 5f, sand_beige.getIndex(), gravel_beige.getIndex(), sandstone_beige_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_brown_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_brown_cobble", 0, 0.45f, 0.6f, 1.5f, sand_brown.getIndex(), gravel_brown.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_brown = WorldRegistries.MATERIALS.register(modid, "sandstone_brown",               0, 0.7f, 0.7f, 5f, sand_brown.getIndex(), gravel_brown.getIndex(), sandstone_brown_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_yellow_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_yellow_cobble",0, 0.45f, 0.6f, 1.5f, sand_yellow.getIndex(), gravel_yellow.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_yellow = WorldRegistries.MATERIALS.register(modid, "sandstone_yellow",             0, 0.7f, 0.7f, 5f, sand_yellow.getIndex(), gravel_yellow.getIndex(), sandstone_yellow_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_pink_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_pink_cobble",   0, 0.45f, 0.6f, 1.5f, sand_pink.getIndex(), gravel_pink.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_pink = WorldRegistries.MATERIALS.register(modid, "sandstone_pink",                 0, 0.7f, 0.7f, 5f, sand_pink.getIndex(), gravel_pink.getIndex(), sandstone_pink_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> sandstone_red_cobble = WorldRegistries.MATERIALS.register(modid, "sandstone_red_cobble",     0, 0.45f, 0.6f, 1.5f, sand_red.getIndex(), gravel_red.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> sandstone_red = WorldRegistries.MATERIALS.register(modid, "sandstone_red",                   0, 0.7f, 0.7f, 5f, sand_red.getIndex(), gravel_red.getIndex(), sandstone_red_cobble.getIndex());

    public static final IndexedContainer<GeoMaterial> limestone_dark_grey_cobble = WorldRegistries.MATERIALS.register(modid, "limestone_dark_grey_cobble", 0, 0.24f, 0.38f, 1f, sand_brown.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> limestone_dark_grey = WorldRegistries.MATERIALS.register(modid, "limestone_dark_grey", 0, 0.32f, 0.18f, 3.5f, sand_brown.getIndex(), gravel_grey.getIndex(), limestone_dark_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> limestone_grey_cobble = WorldRegistries.MATERIALS.register(modid, "limestone_grey_cobble", 0, 0.24f, 0.38f, 1f, sand_brown.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> limestone_grey = WorldRegistries.MATERIALS.register(modid, "limestone_grey", 0, 0.32f, 0.18f, 3.5f, sand_brown.getIndex(), gravel_grey.getIndex(), limestone_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> limestone_beige_cobble = WorldRegistries.MATERIALS.register(modid, "limestone_beige_cobble", 0, 0.24f, 0.38f, 1f, sand_beige.getIndex(), gravel_beige.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> limestone_beige = WorldRegistries.MATERIALS.register(modid, "limestone_beige", 0, 0.32f, 0.18f, 3.5f, sand_beige.getIndex(), gravel_beige.getIndex(), limestone_beige_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> limestone_white_cobble = WorldRegistries.MATERIALS.register(modid, "limestone_white_cobble", 0, 0.24f, 0.38f, 1f, sand_white.getIndex(), gravel_white.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> limestone_white = WorldRegistries.MATERIALS.register(modid, "limestone_white", 0, 0.32f, 0.18f, 3.5f, sand_white.getIndex(), gravel_white.getIndex(), limestone_white_cobble.getIndex());

    public static final IndexedContainer<GeoMaterial> travertine_white_cobble = WorldRegistries.MATERIALS.register(modid, "travertine_white_cobble", 0, 0.18f, 0.38f, 1.75f, sand_white.getIndex(), gravel_white.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> travertine_white = WorldRegistries.MATERIALS.register(modid, "travertine_white", 0, 0.35f, 0.125f, 4f, sand_white.getIndex(), gravel_white.getIndex(), travertine_white_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> travertine_grey_cobble = WorldRegistries.MATERIALS.register(modid, "travertine_grey_cobble", 0, 0.18f, 0.38f, 1.75f, sand_grey.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> travertine_grey = WorldRegistries.MATERIALS.register(modid, "travertine_grey", 0, 0.35f, 0.125f, 4f, sand_grey.getIndex(), gravel_grey.getIndex(), travertine_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> travertine_beige_cobble = WorldRegistries.MATERIALS.register(modid, "travertine_beige_cobble", 0, 0.18f, 0.38f, 1.75f, sand_beige.getIndex(), gravel_beige.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> travertine_beige = WorldRegistries.MATERIALS.register(modid, "travertine_beige", 0, 0.35f, 0.125f, 4f, sand_beige.getIndex(), gravel_beige.getIndex(), travertine_beige_cobble.getIndex());

    public static final IndexedContainer<GeoMaterial> shale_black_cobble = WorldRegistries.MATERIALS.register(modid, "shale_black_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> shale_black = WorldRegistries.MATERIALS.register(modid, "shale_black", 0, 0.76f, 0.48f, 4.5f, sand_black.getIndex(), gravel_grey.getIndex(), shale_black_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> shale_dark_grey_cobble = WorldRegistries.MATERIALS.register(modid, "shale_dark_grey_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> shale_dark_grey = WorldRegistries.MATERIALS.register(modid, "shale_dark_grey", 0, 0.76f, 0.48f, 4.5f, sand_black.getIndex(), gravel_grey.getIndex(), shale_dark_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> shale_grey_cobble = WorldRegistries.MATERIALS.register(modid, "shale_grey_cobble", 0, 0.58f, 0.65f, 0.5f, sand_black.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> shale_grey = WorldRegistries.MATERIALS.register(modid, "shale_grey", 0, 0.76f, 0.48f, 4.5f, sand_black.getIndex(), gravel_grey.getIndex(), shale_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> shale_red_cobble = WorldRegistries.MATERIALS.register(modid, "shale_red_cobble", 0, 0.58f, 0.65f, 0.5f, sand_red.getIndex(), gravel_red.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> shale_red = WorldRegistries.MATERIALS.register(modid, "shale_red", 0, 0.7f, 0.49f, 4.5f, sand_red.getIndex(), gravel_red.getIndex(), shale_red_cobble.getIndex());

    /**
     * Metamorphic Stone
     */
    public static final IndexedContainer<GeoMaterial> slate_grey_cobble = WorldRegistries.MATERIALS.register(modid, "slate_grey_cobble", 0, 0.25f, 0.28f, 21f, clay_beige.getIndex(), gravel_grey.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> slate_grey = WorldRegistries.MATERIALS.register(modid, "slate_grey", 0, 0.38f, 0.075f, 6f, clay_beige.getIndex(), gravel_grey.getIndex(), slate_grey_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> slate_blue_cobble = WorldRegistries.MATERIALS.register(modid, "slate_blue_cobble", 0, 0.25f, 0.28f, 2f, clay_brown.getIndex(), gravel_blue.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> slate_blue = WorldRegistries.MATERIALS.register(modid, "slate_blue", 0, 0.38f, 0.075f, 6f, clay_brown.getIndex(), gravel_blue.getIndex(), slate_blue_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> slate_purple_cobble = WorldRegistries.MATERIALS.register(modid, "slate_purple_cobble", 0, 0.25f, 0.28f, 2f, clay_black.getIndex(), gravel_purple.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> slate_purple = WorldRegistries.MATERIALS.register(modid, "slate_purple", 0, 0.38f, 0.075f, 6f, clay_black.getIndex(), gravel_purple.getIndex(), slate_purple_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> slate_red_cobble = WorldRegistries.MATERIALS.register(modid, "slate_red_cobble", 0, 0.25f, 0.28f, 2f, clay_red.getIndex(), gravel_red.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> slate_red = WorldRegistries.MATERIALS.register(modid, "slate_red", 0, 0.38f, 0.075f, 6f, clay_red.getIndex(), gravel_red.getIndex(), slate_red_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> slate_green_cobble = WorldRegistries.MATERIALS.register(modid, "slate_green_cobble", 0, 0.25f, 0.28f, 2f, clay_yellow.getIndex(), gravel_green.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> slate_green = WorldRegistries.MATERIALS.register(modid, "slate_green", 0, 0.38f, 0.075f, 6f, clay_yellow.getIndex(), gravel_green.getIndex(), slate_green_cobble.getIndex());

    public static final IndexedContainer<GeoMaterial> marble_white_cobble = WorldRegistries.MATERIALS.register(modid, "marble_white_cobble", 0, 0.175f, 0.375f, 2f, sand_white.getIndex(), gravel_white.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_white = WorldRegistries.MATERIALS.register(modid, "marble_white", 0, 0.38f, 0.175f, 2.5f, sand_white.getIndex(), gravel_white.getIndex(), marble_white_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> marble_beige_cobble = WorldRegistries.MATERIALS.register(modid, "marble_beige_cobble", 0, 0.175f, 0.375f, 2f, sand_beige.getIndex(), gravel_beige.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_beige = WorldRegistries.MATERIALS.register(modid, "marble_beige", 0, 0.38f, 0.175f, 2.5f, sand_beige.getIndex(), gravel_beige.getIndex(), marble_beige_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> marble_black_cobble = WorldRegistries.MATERIALS.register(modid, "marble_black_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getIndex(), gravel_black.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_black = WorldRegistries.MATERIALS.register(modid, "marble_black", 0, 0.38f, 0.175f, 2.5f, sand_black.getIndex(), gravel_black.getIndex(), marble_black_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> marble_portoro_cobble = WorldRegistries.MATERIALS.register(modid, "marble_portoro_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getIndex(), gravel_black.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_portoro = WorldRegistries.MATERIALS.register(modid, "marble_portoro", 0, 0.38f, 0.175f, 2.5f, sand_black.getIndex(), gravel_black.getIndex(), marble_portoro_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> marble_green_cobble = WorldRegistries.MATERIALS.register(modid, "marble_green_cobble", 0, 0.175f, 0.375f, 2f, sand_beige.getIndex(), gravel_green.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_green = WorldRegistries.MATERIALS.register(modid, "marble_green", 0, 0.38f, 0.175f, 2.5f, sand_beige.getIndex(), gravel_green.getIndex(), marble_green_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> marble_blue_cobble = WorldRegistries.MATERIALS.register(modid, "marble_blue_cobble", 0, 0.175f, 0.375f, 2f, sand_black.getIndex(), gravel_blue.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> marble_blue = WorldRegistries.MATERIALS.register(modid, "marble_blue", 0, 0.38f, 0.175f, 2.5f, sand_black.getIndex(), gravel_blue.getIndex(), marble_blue_cobble.getIndex());


    /**
     * igneous Stone
     */
    public static final IndexedContainer<GeoMaterial> granite_red_cobble = WorldRegistries.MATERIALS.register(modid, "granite_red_cobble", 0, 0.65f, 0.85f, 4f, sand_red.getIndex(), gravel_red.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> granite_red = WorldRegistries.MATERIALS.register(modid, "granite_red", 0, 0.85f, 0.65f, 24f, sand_red.getIndex(), gravel_red.getIndex(), granite_red_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> granite_pink_cobble = WorldRegistries.MATERIALS.register(modid, "granite_pink_cobble", 0, 0.65f, 0.85f, 4f, sand_pink.getIndex(), gravel_pink.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> granite_pink = WorldRegistries.MATERIALS.register(modid, "granite_pink", 0, 0.85f, 0.65f, 24f, sand_pink.getIndex(), gravel_pink.getIndex(), granite_pink_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> granite_white_cobble = WorldRegistries.MATERIALS.register(modid, "granite_white_cobble", 0, 0.65f, 0.85f, 4f, sand_white.getIndex(), gravel_white.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> granite_white = WorldRegistries.MATERIALS.register(modid, "granite_white", 0, 0.85f, 0.65f, 24f, sand_white.getIndex(), gravel_white.getIndex(), granite_white_cobble.getIndex());
    public static final IndexedContainer<GeoMaterial> basalt_black_cobble = WorldRegistries.MATERIALS.register(modid, "basalt_black_cobble", 0, 0.85f, 0.375f, 3f, sand_black.getIndex(), gravel_black.getIndex(), current());
    public static final IndexedContainer<GeoMaterial> basalt_black = WorldRegistries.MATERIALS.register(modid, "basalt_black", 0, 0.98f, 0.125f, 30f, sand_black.getIndex(), gravel_black.getIndex(), basalt_black_cobble.getIndex());

    private static int current(){
        return WorldRegistries.MATERIALS.getEntries().size();
    }

    public static void init(){
        WildsRegrown.LOGGER.info("Registered materials");

        for (GeoMaterial object : WorldRegistries.MATERIALS.getArray()){
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
