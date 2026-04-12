package com.wildsregrown.registries.world.biomes.grassfield;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class SavannaGrassfield extends Biome {

    public SavannaGrassfield() {
        setTreeDensity(8f);
        register(Floras.curly_grass, FloraSpawnRule.grouped, 0.5f, 1,true);
        register(Trees.oak, 0.5f, 1);
        register(Floras.thin_grass, FloraSpawnRule.dotted, 0.5f, 1,true);
        register(Floras.dandelion, FloraSpawnRule.occasional, 0.5f, 1,true);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.savannaTemple);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
