package com.wildsregrown.registries.world.biomes.meadows.temperate;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class CloverMeadow extends Biome {

    public CloverMeadow() {
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.grouped, 1, 1,true);
        register(Floras.clover_pink, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.clover_red, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
