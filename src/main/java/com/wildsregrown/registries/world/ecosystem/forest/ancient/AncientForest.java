package com.wildsregrown.registries.world.ecosystem.forest.ancient;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class AncientForest extends Ecosystem {

    public AncientForest(){
        super(Climate.ancientForest, Biomes.ancientForest);
        register(Placement.Biome.dry, Biomes.birch_meadow);
        register(Placement.Biome.wet, Biomes.jacarandaForest);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
