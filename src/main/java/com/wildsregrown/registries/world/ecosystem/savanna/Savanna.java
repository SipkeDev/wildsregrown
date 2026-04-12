package com.wildsregrown.registries.world.ecosystem.savanna;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class Savanna extends Ecosystem {

    public Savanna(){
        super(Climate.savanna, Biomes.savanna_grassfield);
        register(Placement.Biome.dry, Biomes.savanna_grassfield);
        register(Placement.Biome.dry, Biomes.savannaShrubs);
        register(Placement.Biome.wet, Biomes.savannaTrees);
        register(Placement.Biome.wet, Biomes.savannaWoodlands);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
