package com.wildsregrown.registries.world.ecosystem.steppe;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class SageBushSteppe extends Ecosystem {

    public SageBushSteppe(){
        super(Climate.steppe, Biomes.sageshrub_plains);
        register(Placement.Biome.swamp, Biomes.white_flower_grassfield);
        register(Placement.Biome.dry, Biomes.dry_sageshrub_plains);
        register(Placement.Biome.wet, Biomes.wet_sageshrub_plains);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
