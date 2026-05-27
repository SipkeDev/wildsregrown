package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;
import com.wildsregrown.registries.world.Structures;

import static com.wildsregrown.WildsRegrown.modid;

public class Ice extends Biome {

    public Ice() {
        super(modid, "ice");
        setSurface(MaterialRegistery.snow, 3f);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
