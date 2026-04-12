package com.wildsregrown.registries.world.ecosystem.shrubland;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class CoolShrubLands extends Ecosystem {

    public CoolShrubLands(){
        super(Climate.coolShrubland, Biomes.coolShrubland);
        register(Placement.Biome.dry, Biomes.coolDesert);
        register(Placement.Biome.wet, Biomes.sageshrub_plains);
        register(Placement.Biome.swamp, Biomes.wet_sageshrub_plains);
    }

}
