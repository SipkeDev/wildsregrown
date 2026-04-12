package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

public class HotDesert extends Biome {

    public HotDesert() {
        setOvergrown(false);
        setSurface(MaterialRegistery.sand_yellow, 3f);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
