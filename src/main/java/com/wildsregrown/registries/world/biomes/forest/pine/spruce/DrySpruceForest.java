package com.wildsregrown.registries.world.biomes.forest.pine.spruce;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class DrySpruceForest extends Biome {

    public DrySpruceForest() {
        setTreeDensity(1.43f);
        register(Trees.spruce, 0.125f, 0.34f);
        register(Floras.grass, FloraSpawnRule.dotted, 0, 0.75f,false);
        register(Floras.curly_grass, FloraSpawnRule.occasional, 0, 0.75f,false);
        register(Floras.artiplex_shining, FloraSpawnRule.occasional, 0, 0.75f,false);
        register(Floras.dandelion, FloraSpawnRule.rare, 0, 0.75f,false);
        register(Structures.spruceCabin);
        register(Structures.smallSpruceCabin);
        register(Structures.spruceHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
