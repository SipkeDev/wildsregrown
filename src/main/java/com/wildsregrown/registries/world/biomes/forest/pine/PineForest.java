package com.wildsregrown.registries.world.biomes.forest.pine;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class PineForest extends Biome {

    public PineForest() {
        super(modid, "pine_forest");
        setTreeDensity(1.4f);
        register(Trees.spruce, 0.25f, 1);
        register(Trees.larch, 0.25f, 1);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.chives_lilac, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.spruceCabin);
        register(Structures.spruceHut);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 121).fbm(3);
    }

}
