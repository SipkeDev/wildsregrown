package com.wildsregrown.registries.world.ecosystem.sea;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class Sea extends Ecosystem {

    public Sea(){
        super(Climate.temperateSea, Biomes.sea);
    }

}
