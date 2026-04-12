package com.wildsregrown.registries.world.biomes.sea;

import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

public class Sea extends Biome {

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
