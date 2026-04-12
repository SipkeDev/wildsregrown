package com.wildsregrown.registries.world.ecosystem.tundra;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

public class Tundra extends Ecosystem {

    public Tundra(){
        super(Climate.tundra, Biomes.sparse_grassfield);
    }

}
