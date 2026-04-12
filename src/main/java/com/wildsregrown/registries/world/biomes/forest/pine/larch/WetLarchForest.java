package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class WetLarchForest extends Biome {

    public WetLarchForest() {
        register(Trees.larch, 0.5f, 1);
        register(Trees.silver_birch, 0.5f, 1);
        register(Floras.curly_grass, FloraSpawnRule.full_coverage, 0.75f, 1,false);
        register(Floras.tall_curly_grass, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Structures.larchHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
