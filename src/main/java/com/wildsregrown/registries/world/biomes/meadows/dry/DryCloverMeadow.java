package com.wildsregrown.registries.world.biomes.meadows.dry;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class DryCloverMeadow extends Biome {

    public DryCloverMeadow() {
        setOvergrown(false);
        register(Floras.grass, FloraSpawnRule.grouped, 0.25f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.clover_pink, FloraSpawnRule.rare, 1, 1,true);
        register(Floras.clover_red, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
