package com.wildsregrown.registries.world.biomes.grassfield.temperate;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;

public class Grassfield extends Biome {

    public Grassfield() {
        register(Floras.grass, FloraSpawnRule.grouped, 0f, 1,false);
        register(Floras.tall_grass, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.ruinedHouse);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
