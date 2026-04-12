package com.wildsregrown.registries.world.biomes.grassfield.wet;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class WetGrassfield extends Biome {

    public WetGrassfield() {
        register(Floras.grass, FloraSpawnRule.full_coverage, 0f, 1,false);
        register(Floras.tall_grass, FloraSpawnRule.dotted, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
