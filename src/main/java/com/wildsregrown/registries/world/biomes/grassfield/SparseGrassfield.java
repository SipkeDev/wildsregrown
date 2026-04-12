package com.wildsregrown.registries.world.biomes.grassfield;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;

public class SparseGrassfield extends Biome {

    public SparseGrassfield() {
        setOvergrown(false);
        register(Floras.thin_grass, FloraSpawnRule.dotted, 0f, 1,false);
        register(Floras.grass, FloraSpawnRule.rare, 0f, 1,false);
        register(Structures.cliffOutpost);
        register(Structures.abandonedOutpost);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

    
}
