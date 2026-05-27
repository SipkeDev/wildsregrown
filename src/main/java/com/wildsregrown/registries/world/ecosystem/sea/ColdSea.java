package com.wildsregrown.registries.world.ecosystem.sea;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class ColdSea extends Ecosystem {

    public ColdSea(){
        super(modid, "cold_sea", Climate.coldSea, Biomes.coldSea);
    }

}
