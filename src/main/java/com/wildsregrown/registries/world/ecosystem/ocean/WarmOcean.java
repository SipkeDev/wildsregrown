package com.wildsregrown.registries.world.ecosystem.ocean;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class WarmOcean extends Ecosystem {

    public WarmOcean(){
        super(modid, "warm_ocean", Climate.warmOcean, Biomes.warmSea);
    }

}
