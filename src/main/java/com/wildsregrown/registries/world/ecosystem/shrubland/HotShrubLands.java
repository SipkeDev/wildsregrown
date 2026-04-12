package com.wildsregrown.registries.world.ecosystem.shrubland;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class HotShrubLands extends Ecosystem {

    public HotShrubLands(){
        super(Climate.hotScrubland, Biomes.hotShrubland);
        register(Placement.Biome.dry, Biomes.hotShrubland);
        register(Placement.Biome.wet, Biomes.dry_sageshrub_plains);
        register(Placement.Biome.swamp, Biomes.sageshrub_plains);
    }

}
