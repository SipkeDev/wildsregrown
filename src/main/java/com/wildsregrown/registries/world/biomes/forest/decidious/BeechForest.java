package com.wildsregrown.registries.world.biomes.forest.decidious;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class BeechForest extends Biome {

    public BeechForest() {
        register(Trees.beech, 0.5f, 0.75f);
        register(Floras.thin_grass, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.grass, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.nettle, FloraSpawnRule.rare, 0.75f, 1,false);
        register(Structures.altar);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32).fbm(2);
    }

}
