package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.Floras;

public class DaisyMeadow extends Biome {

    public DaisyMeadow() {
        register(Floras.grass, FloraSpawnRule.full_coverage, 1, 1,true);
        register(Floras.daisy, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }
}
