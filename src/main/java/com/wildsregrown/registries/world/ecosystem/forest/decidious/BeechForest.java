package com.wildsregrown.registries.world.ecosystem.forest.decidious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class BeechForest extends Ecosystem {

    public BeechForest(){
        super(modid, "beech_forest", Climate.deciduousForest, Biomes.beechForest, 5);
        register(Placement.Biome.normal, Biomes.sparse_beechForest, 5);
        register(Placement.Biome.normal, Biomes.daisy_meadow);
        register(Placement.Biome.normal, Biomes.clover_meadow);
        register(Placement.Biome.dry, Biomes.dry_clover_meadow);
        register(Placement.Biome.dry, Biomes.dry_beechForest, 3);
        register(Placement.Biome.wet, Biomes.birch_meadow);
        register(Placement.Biome.wet, Biomes.wet_clover_meadow);
        register(Placement.Biome.wet, Biomes.wet_beechForest, 3);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
