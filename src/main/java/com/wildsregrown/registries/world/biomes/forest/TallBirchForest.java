package com.wildsregrown.registries.world.biomes.forest;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

public class TallBirchForest extends Biome {

    public TallBirchForest() {
        register(Trees.tall_birch,0, 1);
        register(Floras.grass, FloraSpawnRule.grouped, 0, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        return NoiseGenerator.cubic(seed.next(), 128).fbm(3);
    }
}
