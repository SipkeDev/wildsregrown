package com.wildsregrown.registries.world.biomes.grassfield.dry;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;

public class DryGrassfield extends Biome {

    public DryGrassfield() {
        setOvergrown(false);
        register(Floras.grass, FloraSpawnRule.dotted, 0f, 1,false);
        register(Floras.tall_grass, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.cliffOutpost);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
