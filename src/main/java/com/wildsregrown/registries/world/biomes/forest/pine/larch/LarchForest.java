package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class LarchForest extends Biome {

    public LarchForest() {
        super(modid, "larch_forest");
        register(Trees.larch, 0.75f, 1);
        register(Floras.curly_grass, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.daisy, FloraSpawnRule.dotted, 0.75f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }
}
