package com.wildsregrown.registries.world.biomes.forest;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class SavannaTrees extends Biome {

    public SavannaTrees() {
        super(modid, "savanna_trees");
        setTreeDensity(2f);
        register(Trees.ash, 0.25f, 1);
        register(Trees.pear, 0.25f, 1);
        register(Floras.curly_grass, FloraSpawnRule.grouped, 0.25f, 1,false);
        register(Floras.dandelion, FloraSpawnRule.occasional, 0.25f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
