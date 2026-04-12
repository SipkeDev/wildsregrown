package com.wildsregrown.registries.world.ecosystem.ocean;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class Ocean extends Ecosystem {

    public Ocean(){
        super(Climate.temperateOcean, Biomes.sea);
    }

}
