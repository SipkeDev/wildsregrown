package com.wildsregrown.registries.world.ecosystem.desert;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class CoolDesert extends Ecosystem {

    public CoolDesert(){
        super(Climate.coolDesert, Biomes.coolDesert);
        register(Placement.Biome.swamp, Biomes.swamp);
    }

}
