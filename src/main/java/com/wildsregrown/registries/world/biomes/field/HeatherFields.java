package com.wildsregrown.registries.world.biomes.field;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.MaterialRegistery;

public class HeatherFields extends Biome {

    public HeatherFields() {
        setSurface(MaterialRegistery.sand_beige, 3f);
        register(Floras.heather_lilac, FloraSpawnRule.grouped, 0.75f, 1,true);
        register(Floras.heather_purple, FloraSpawnRule.dotted, 0.75f, 1,true);
        register(Floras.heather_red, FloraSpawnRule.occasional, 0.75f, 1,true);
        register(Floras.curly_grass, FloraSpawnRule.full_coverage, 0.75f, 1,true);
        //register(Structures.small_tower);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
