
package com.wildsregrown.registries.world.biomes.forest.pine.spruce;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class WetSpruceForest extends Biome {

    public WetSpruceForest() {
        register(Trees.spruce, 0.75f, 1);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0, 0.75f,false);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0, 0.75f,false);
        register(Floras.dandelion, FloraSpawnRule.occasional, 0, 0.75f,false);
        register(Floras.nettle, FloraSpawnRule.rare, 0, 0.75f,false);
        register(Structures.spruceCabin);
        register(Structures.smallSpruceCabin);
        register(Structures.spruceHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
