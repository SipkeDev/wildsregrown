package com.wildsregrown.registries.world.biomes.sea;

import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

import static com.wildsregrown.WildsRegrown.modid;

public class WarmSea extends Biome {

    public WarmSea(){
        super(modid, "warm_sea");
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
