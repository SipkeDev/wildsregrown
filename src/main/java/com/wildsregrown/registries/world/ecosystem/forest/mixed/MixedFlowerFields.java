package com.wildsregrown.registries.world.ecosystem.forest.mixed;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class MixedFlowerFields extends Ecosystem {

    public MixedFlowerFields(){
        super(modid, "mixed_flower_fields", Climate.mixedForest,  Biomes.dense_grassfield);
        register(Placement.Biome.normal, Biomes.birch_meadow);
        register(Placement.Biome.normal, Biomes.clover_meadow);
        register(Placement.Biome.normal, Biomes.lily_meadow);
        register(Placement.Biome.normal, Biomes.spirea_meadow);
        register(Placement.Biome.normal, Biomes.oak_meadow);
        register(Placement.Biome.normal, Biomes.mixed_flower_meadow);
        register(Placement.Biome.normal, Biomes.birch_meadow);
        register(Placement.Biome.dry, Biomes.sparse_grassfield);
        register(Placement.Biome.dry, Biomes.dry_clover_meadow);
        register(Placement.Biome.dry, Biomes.daisy_meadow);
        register(Placement.Biome.wet, Biomes.orchid_meadow);
        register(Placement.Biome.wet, Biomes.campanula_meadow);
        register(Placement.Biome.wet, Biomes.foxglove_meadow);
        register(Placement.Biome.wet, Biomes.tulip_fields);
        register(Placement.Biome.wet, Biomes.dense_larchForest);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
