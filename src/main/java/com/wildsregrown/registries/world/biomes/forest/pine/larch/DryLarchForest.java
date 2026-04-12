package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class DryLarchForest extends Biome {

    public DryLarchForest() {
        setTreeDensity(1.215f);
        register(Trees.larch, 0.25f, 0.5f);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.75f, 1,false);
        register(Floras.daisy, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Structures.larchHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 64).fbm(3);
    }

}
