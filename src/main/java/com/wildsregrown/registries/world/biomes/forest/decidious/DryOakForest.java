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

public class DryOakForest extends Biome {

    public DryOakForest() {
        super(modid, "dry_oak_forest");
        setTreeDensity(1.57f);
        register(Trees.oak, 0.125f, 0.5f);
        register(Floras.curly_grass, FloraSpawnRule.occasional, 0.25f, 1,false);
        register(Structures.small_ruins);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 64);
    }

}
