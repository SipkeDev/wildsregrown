package com.wildsregrown.registries.world.ecosystem.forest.coniferious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class LarchForest extends Ecosystem {

    public LarchForest(){
        super(Climate.coniferousForest, Biomes.larchForest, 13);
        register(Placement.Biome.normal, Biomes.larchForest_flowers);
        register(Placement.Biome.normal, Biomes.larchForest_artiplex, 3);
        register(Placement.Biome.normal, Biomes.dense_larchForest, 2);
        register(Placement.Biome.dry, Biomes.dry_larchForest, 8);
        register(Placement.Biome.dry, Biomes.sparse_larchForest, 5);
        register(Placement.Biome.dry, Biomes.daisy_meadow);
        register(Placement.Biome.wet, Biomes.lily_meadow);
        register(Placement.Biome.wet, Biomes.dense_grassfield);
        register(Placement.Biome.wet, Biomes.wet_larchForest, 13);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.swamp, Biomes.swamp_larchForest, 8);
        register(Placement.Biome.river, Biomes.temperateRiver);
        register(Placement.Biome.river, Biomes.river_larchForest, 3);
        register(Placement.Biome.lake, Biomes.lake_larchForest, 3);
        register(Placement.Biome.lake, Biomes.temperateLake);
    }

}
