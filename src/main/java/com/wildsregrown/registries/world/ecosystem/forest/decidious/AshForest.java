package com.wildsregrown.registries.world.ecosystem.forest.decidious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class AshForest extends Ecosystem {

    public AshForest(){
        super(modid, "ash_forest", Climate.deciduousForest, Biomes.ashForest, 5);
        register(Placement.Biome.normal, Biomes.sparse_ashForest, 2);
        register(Placement.Biome.dry, Biomes.mixed_flower_grassfield);
        register(Placement.Biome.dry, Biomes.dry_ashForest, 3);
        register(Placement.Biome.wet, Biomes.lily_meadow);
        register(Placement.Biome.wet, Biomes.mixed_flower_meadow);
        register(Placement.Biome.wet, Biomes.wet_ashForest, 3);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
