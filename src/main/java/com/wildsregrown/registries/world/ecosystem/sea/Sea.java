package com.wildsregrown.registries.world.ecosystem.sea;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class Sea extends Ecosystem {

    public Sea(){
        super(modid, "sea", Climate.temperateSea, Biomes.sea);
    }

}
