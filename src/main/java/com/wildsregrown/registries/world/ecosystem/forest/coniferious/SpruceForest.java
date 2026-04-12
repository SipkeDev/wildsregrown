package com.wildsregrown.registries.world.ecosystem.forest.coniferious;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class SpruceForest extends Ecosystem {

    public SpruceForest(){
        super(Climate.coniferousForest, Biomes.spruceForest);
        register(Placement.Biome.dry, Biomes.dry_spruceForest, 5);
        register(Placement.Biome.dry, Biomes.sparse_grassfield);
        register(Placement.Biome.wet, Biomes.wet_spruceForest, 5);
        register(Placement.Biome.wet, Biomes.mixed_flower_grassfield);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
