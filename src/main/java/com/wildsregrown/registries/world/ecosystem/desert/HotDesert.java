package com.wildsregrown.registries.world.ecosystem.desert;

import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class HotDesert extends Ecosystem {

    public HotDesert(){
        super(modid, "hot_desert", Climate.hotDesert, Biomes.hotDesert);
    }

}
