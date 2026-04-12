package com.wildsregrown.registries.world.ecosystem.forest.spirit;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class SpiritForest extends Ecosystem {

    public SpiritForest(){
        super(Climate.spiritForest, Biomes.spiritForest);
        register(Placement.Biome.dry, Biomes.dry_heather_meadow);
        register(Placement.Biome.wet, Biomes.grassfield);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
