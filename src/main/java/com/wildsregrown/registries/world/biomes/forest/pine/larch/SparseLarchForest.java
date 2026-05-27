package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class SparseLarchForest extends Biome {

    public SparseLarchForest() {
        super(modid, "sparse_larch_forest");
        setTreeDensity(1.57f);
        register(Trees.larch, 0.25f, 0.75f);
        register(Trees.dwarf_birch, 0.25f, 0.75f);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32);
    }
}
