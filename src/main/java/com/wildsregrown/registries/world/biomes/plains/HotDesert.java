package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class HotDesert extends Biome {

    public HotDesert() {
        super(modid, "hot_desert");
        setOvergrown(false);
        setSurface(MaterialRegistery.sand_yellow, 3f);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
