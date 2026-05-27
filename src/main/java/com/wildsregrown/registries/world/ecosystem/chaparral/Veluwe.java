package com.wildsregrown.registries.world.ecosystem.chaparral;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class Veluwe extends Ecosystem {

    public Veluwe(){
        super(modid, "veluwe", Climate.chaparral, Biomes.heather_field, 5);
        register(Placement.Biome.normal, Biomes.heather_meadow);
        register(Placement.Biome.dry, Biomes.dry_heather_meadow, 3);
        register(Placement.Biome.dry, Biomes.grassfield);
        register(Placement.Biome.wet, Biomes.heather_field, 2);
        register(Placement.Biome.wet, Biomes.wet_larchForest);
        register(Placement.Biome.wet, Biomes.beechForest);
        register(Placement.Biome.wet, Biomes.wet_oakForest);
        register(Placement.Biome.swamp, Biomes.swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
