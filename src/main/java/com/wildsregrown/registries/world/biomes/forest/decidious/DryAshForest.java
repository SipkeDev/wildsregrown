package com.wildsregrown.registries.world.biomes.forest.decidious;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class DryAshForest extends Biome {

    public DryAshForest() {
        setOvergrown(false);
        setTreeDensity(2f);
        register(Trees.ash, 0.75f, 1);
        register(Floras.curly_grass, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.grass, FloraSpawnRule.dotted, 0.75f, 1,false);
        register(Structures.small_ruins);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 16).fbm(2);
    }

}
