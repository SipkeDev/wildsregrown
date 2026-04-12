package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

public class LakeLarchForest extends Biome {

    public LakeLarchForest() {
        register(Trees.weeping_willow, 0.2f, 1);
        register(Floras.tall_grass, FloraSpawnRule.full_coverage, 0.75f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
