package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class TulipFields extends Biome {

    public TulipFields() {
        register(Floras.tulip_red, FloraSpawnRule.full_coverage, 1, 1, true);
        register(Floras.tulip_white, FloraSpawnRule.dotted, 1, 1, true);
        register(Floras.tulip_orange, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_pink, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_yellow, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_purple, FloraSpawnRule.rare, 1, 1, true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}