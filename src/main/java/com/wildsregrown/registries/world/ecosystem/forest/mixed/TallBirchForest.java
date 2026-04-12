package com.wildsregrown.registries.world.ecosystem.forest.mixed;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class TallBirchForest extends Ecosystem {

    public TallBirchForest(){
        super(Climate.mixedForest,  Biomes.tall_birch_forest, 6);
        register(Placement.Biome.normal, Biomes.abandonedFarmland);
        register(Placement.Biome.dry, Biomes.grassfield);
        register(Placement.Biome.wet, Biomes.birch_meadow);
        register(Placement.Biome.swamp, Biomes.tall_birch_swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
