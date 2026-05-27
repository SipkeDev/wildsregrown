package com.wildsregrown.registries.world.biomes.forest.decidious;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class SparseOakForest extends Biome {

    public SparseOakForest() {
        super(modid, "sparse_oak_forest");
        setTreeDensity(1.785f);
        register(Trees.oak, 0.125f, 0.75f);
        register(Floras.thin_grass, FloraSpawnRule.full_coverage, 0.75f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.artiplex_shining, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.dandelion, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.grass, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.nettle, FloraSpawnRule.rare, 0.75f, 1,false);
        register(Structures.small_ruins);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 64).fbm(2);
    }

}
