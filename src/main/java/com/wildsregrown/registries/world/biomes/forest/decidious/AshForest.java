package com.wildsregrown.registries.world.biomes.forest.decidious;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class AshForest extends Biome {

    public AshForest() {
        super(modid, "ash_forest");
        register(Trees.ash, 0.25f, 0.75f);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 121).fbm(3);
    }

}
