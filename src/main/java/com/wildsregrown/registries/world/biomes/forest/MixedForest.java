package com.wildsregrown.registries.world.biomes.forest;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class MixedForest extends Biome {

    public MixedForest() {
        setTreeDensity(1.2f);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.curly_grass, FloraSpawnRule.occasional, 0.25f, 1,false);
        register(Floras.nettle, FloraSpawnRule.dotted, 0.25f, 1,false);
        register(Trees.larch, 0.25f, 1);
        register(Trees.oak, 0.25f, 1);
        register(Trees.silver_birch, 0.25f, 0.5f);
        register(Trees.weeping_willow, 0.25f, 1);
        register(Trees.ash, 0.25f, 0.5f);
        register(Trees.beech, 0.25f, 0.5f);
        register(Floras.chives_purple, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.spruceCabin);
        register(Structures.larchHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 44);
    }

}
