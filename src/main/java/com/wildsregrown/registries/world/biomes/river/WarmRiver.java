package com.wildsregrown.registries.world.biomes.river;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class WarmRiver extends Biome {

    public WarmRiver() {
        super(modid, "warm_river");
        setSurface(MaterialRegistery.sand_yellow, 1);
        register(Floras.thin_grass, FloraSpawnRule.full_coverage, 0.5f, 1,false);
        register(Floras.nettle, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_lilac, FloraSpawnRule.rare, 0.5f, 1,false);
    }

}
