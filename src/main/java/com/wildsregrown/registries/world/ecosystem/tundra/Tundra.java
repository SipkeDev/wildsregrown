package com.wildsregrown.registries.world.ecosystem.tundra;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class Tundra extends Ecosystem {

    public Tundra(){
        super(modid, "tundra", Climate.tundra, Biomes.sparse_grassfield);
    }

}
