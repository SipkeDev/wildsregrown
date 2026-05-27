package com.wildsregrown.registries.world.ecosystem.desert;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class CoolDesert extends Ecosystem {

    public CoolDesert(){
        super(modid, "cool_desert", Climate.coolDesert, Biomes.coolDesert);
        register(Placement.Biome.swamp, Biomes.swamp);
    }

}
