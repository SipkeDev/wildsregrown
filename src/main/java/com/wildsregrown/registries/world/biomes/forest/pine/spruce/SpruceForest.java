package com.wildsregrown.registries.world.biomes.forest.pine.spruce;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class SpruceForest extends Biome {

    public SpruceForest() {
        super(modid, "spruce_forest");
        register(Trees.spruce, 0.25f, 0.75f);
        register(Floras.grass, FloraSpawnRule.grouped, 0, 0.75f,false);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0, 0.75f,false);
        register(Floras.dandelion, FloraSpawnRule.occasional, 0, 0.75f,false);
        register(Structures.spruceCabin);
        register(Structures.spruceHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
