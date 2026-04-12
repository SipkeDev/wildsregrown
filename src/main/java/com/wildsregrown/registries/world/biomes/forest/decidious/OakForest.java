package com.wildsregrown.registries.world.biomes.forest.decidious;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class OakForest extends Biome {

    public OakForest() {
        register(Trees.oak, 0.25f, 1);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);
        register(Structures.altar);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 121).fbm(3);
    }

}
