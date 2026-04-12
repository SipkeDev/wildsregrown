package com.wildsregrown.registries.world.ecosystem.forest.mixed;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class KuunderForest extends Ecosystem {

    public KuunderForest(){
        super(Climate.mixedForest, Biomes.mixedForest);
        register(Placement.Biome.normal, Biomes.larchForest);
        register(Placement.Biome.normal, Biomes.abandonedFarmland);
        register(Placement.Biome.dry, Biomes.spruceForest);
        register(Placement.Biome.dry, Biomes.daisy_meadow);
        register(Placement.Biome.dry, Biomes.grassfield);
        register(Placement.Biome.wet, Biomes.beechForest);
        register(Placement.Biome.wet, Biomes.oakForest);
        register(Placement.Biome.wet, Biomes.birch_meadow);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.swamp, Biomes.kwelder);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
