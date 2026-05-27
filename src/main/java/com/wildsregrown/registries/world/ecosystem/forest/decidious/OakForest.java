package com.wildsregrown.registries.world.ecosystem.forest.decidious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class OakForest extends Ecosystem {

    public OakForest(){
        super(modid, "oak_forest", Climate.deciduousForest, Biomes.oakForest, 5);
        register(Placement.Biome.normal, Biomes.sparse_oakForest,3);
        register(Placement.Biome.normal, Biomes.grassfield);
        register(Placement.Biome.dry, Biomes.daisy_meadow);
        register(Placement.Biome.dry, Biomes.dry_oakForest, 5);
        register(Placement.Biome.wet, Biomes.birch_meadow);
        register(Placement.Biome.wet, Biomes.lily_meadow);
        register(Placement.Biome.wet, Biomes.wet_oakForest, 5);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.swamp, Biomes.wet_clover_meadow);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
