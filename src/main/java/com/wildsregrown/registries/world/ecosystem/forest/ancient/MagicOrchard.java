package com.wildsregrown.registries.world.ecosystem.forest.ancient;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class MagicOrchard extends Ecosystem {

    public MagicOrchard(){
        super(modid, "magic_orchard", Climate.ancientForest, Biomes.apple_orchard);
        register(Placement.Biome.dry, Biomes.heather_field);
        register(Placement.Biome.normal, Biomes.apple_orchard);
        register(Placement.Biome.normal, Biomes.pear_orchard);
        register(Placement.Biome.normal, Biomes.plum_orchard);
        register(Placement.Biome.wet, Biomes.birch_meadow);
        register(Placement.Biome.wet, Biomes.dense_tall_grassfield);
        register(Placement.Biome.wet, Biomes.abandonedFarmland);
        register(Placement.Biome.swamp, Biomes.magic_swamp);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
