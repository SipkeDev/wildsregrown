package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class LarchForestArtiPlex extends Biome {

    public LarchForestArtiPlex() {
        register(Trees.larch, 0.75f, 1);
        register(Floras.artiplex_shining, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.artiplex_green, FloraSpawnRule.dotted, 0.75f, 1,false);
        register(Floras.nettle, FloraSpawnRule.rare, 0.75f, 1,false);
        register(Structures.larchHouse);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32);
    }

}
