package com.wildsregrown.registries.world.ecosystem.forest.decidious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class DecidiousGrassfield extends Ecosystem {

    public DecidiousGrassfield(){
        super(Climate.deciduousForest, Biomes.grassfield);
        register(Placement.Biome.normal, Biomes.sparse_oakForest);
        register(Placement.Biome.normal, Biomes.sparse_beechForest);
        register(Placement.Biome.normal, Biomes.sparse_larchForest);
        register(Placement.Biome.normal, Biomes.oakForest);
        register(Placement.Biome.normal, Biomes.larchForest_flowers);
        register(Placement.Biome.dry, Biomes.dry_clover_meadow);
        register(Placement.Biome.dry, Biomes.dry_grassfield);
        register(Placement.Biome.dry, Biomes.sparse_grassfield);
        register(Placement.Biome.wet, Biomes.wet_oakForest);
        register(Placement.Biome.wet, Biomes.wet_beechForest);
        register(Placement.Biome.wet, Biomes.hydrangea_meadow);
        register(Placement.Biome.wet, Biomes.poppy_meadow);
        register(Placement.Biome.wet, Biomes.mixed_flower_grassfield);
        register(Placement.Biome.wet, Biomes.wet_grassfield);
        register(Placement.Biome.wet, Biomes.abandonedFarmland);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
