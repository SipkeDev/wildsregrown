package com.wildsregrown.registries.world.ecosystem.forest.coniferious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class PineForest extends Ecosystem {

    public PineForest() {
        super(Climate.coniferousForest, Biomes.pineForest);
        register(Placement.Biome.normal, Biomes.spruceForest);
        register(Placement.Biome.normal, Biomes.larchForest);
        register(Placement.Biome.normal, Biomes.larchForest_flowers);
        register(Placement.Biome.normal, Biomes.heather_meadow);
        register(Placement.Biome.dry, Biomes.dry_heather_meadow);
        register(Placement.Biome.dry, Biomes.sparse_grassfield);
        register(Placement.Biome.dry, Biomes.sparse_larchForest);
        register(Placement.Biome.dry, Biomes.dry_larchForest);
        register(Placement.Biome.dry, Biomes.dry_spruceForest);
        register(Placement.Biome.wet, Biomes.campanula_meadow);
        register(Placement.Biome.wet, Biomes.foxglove_meadow);
        register(Placement.Biome.wet, Biomes.dense_larchForest);
        register(Placement.Biome.wet, Biomes.wet_larchForest);
        register(Placement.Biome.wet, Biomes.wet_spruceForest);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
