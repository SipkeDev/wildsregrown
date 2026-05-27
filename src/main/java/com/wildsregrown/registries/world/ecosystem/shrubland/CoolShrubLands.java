package com.wildsregrown.registries.world.ecosystem.shrubland;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class CoolShrubLands extends Ecosystem {

    public CoolShrubLands(){
        super(modid, "cool_shrubland", Climate.coolShrubland, Biomes.coolShrubland);
        register(Placement.Biome.dry, Biomes.coolDesert);
        register(Placement.Biome.wet, Biomes.sageshrub_plains);
        register(Placement.Biome.swamp, Biomes.wet_sageshrub_plains);
    }

}
