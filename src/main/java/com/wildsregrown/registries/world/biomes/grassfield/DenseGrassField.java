package com.wildsregrown.registries.world.biomes.grassfield;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

public class DenseGrassField extends Biome {

    public DenseGrassField() {
        setTreeDensity(12f);
        register(Trees.silver_birch, 0, 1);
        register(Trees.dwarf_birch, 0, 1);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0f, 1,false);
        register(Floras.curly_grass, FloraSpawnRule.grouped, 0f, 1,false);
        register(Floras.tall_grass, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
        register(Structures.ruinedCastle);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        return NoiseGenerator.cubic(seed.next(), 128).fbm(3);
    }
}