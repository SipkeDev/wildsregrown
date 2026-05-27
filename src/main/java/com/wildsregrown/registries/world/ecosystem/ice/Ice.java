package com.wildsregrown.registries.world.ecosystem.ice;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class Ice extends Ecosystem {

    public Ice(){
        super(modid, "ice", Climate.ice, Biomes.ice);
    }

}