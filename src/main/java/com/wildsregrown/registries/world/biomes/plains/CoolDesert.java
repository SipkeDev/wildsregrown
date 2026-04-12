package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.MaterialRegistery;

public class CoolDesert extends Biome {

    public CoolDesert() {
        setSurface(MaterialRegistery.sand_beige, 5f);
        setOvergrown(false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
