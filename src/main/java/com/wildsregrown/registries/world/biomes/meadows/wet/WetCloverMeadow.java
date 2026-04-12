package com.wildsregrown.registries.world.biomes.meadows.wet;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class WetCloverMeadow extends Biome {

    public WetCloverMeadow() {
        register(Floras.clover_white, FloraSpawnRule.full_coverage, 1, 1,true);
        register(Floras.clover_pink, FloraSpawnRule.grouped, 1, 1,true);
        register(Floras.clover_red, FloraSpawnRule.occasional, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
