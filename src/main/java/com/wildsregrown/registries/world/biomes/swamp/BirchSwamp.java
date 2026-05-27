package com.wildsregrown.registries.world.biomes.swamp;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class BirchSwamp extends Biome {

    public BirchSwamp() {
        super(modid, "birch_swamp");
        register(Trees.silver_birch,0, 1);
        register(Floras.grass, FloraSpawnRule.grouped, 0, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        return NoiseGenerator.constant(-0.5f);
    }
}
