package com.wildsregrown.registries.world.ecosystem.forest.mixed;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class DrintskFryskWold extends Ecosystem {

    public DrintskFryskWold(){
        super(Climate.mixedForest, Biomes.larchForest);
        register(Placement.Biome.normal, Biomes.larchForest_artiplex);
        register(Placement.Biome.normal, Biomes.spruceForest);
        register(Placement.Biome.normal, Biomes.heather_meadow);
        register(Placement.Biome.dry, Biomes.dry_heather_meadow);
        register(Placement.Biome.dry, Biomes.sparse_larchForest);
        register(Placement.Biome.dry, Biomes.spruceForest);
        register(Placement.Biome.wet, Biomes.dense_larchForest, 4);
        register(Placement.Biome.wet, Biomes.wet_spruceForest);
        register(Placement.Biome.wet, Biomes.wet_larchForest, 3);
        register(Placement.Biome.wet, Biomes.mixed_flower_grassfield);
        register(Placement.Biome.wet, Biomes.red_flower_grassfield);
        register(Placement.Biome.wet, Biomes.white_flower_grassfield);
        register(Placement.Biome.swamp, Biomes.swamp_larchForest, 3);
        register(Placement.Biome.swamp, Biomes.kwelder);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.lake, Biomes.river_larchForest, 3);
        register(Placement.Biome.river, Biomes.temperateRiver);
        register(Placement.Biome.river, Biomes.river_larchForest, 3);
    }

}