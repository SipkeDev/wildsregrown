package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class AbandonedFarmLand extends Biome {

    public AbandonedFarmLand() {
        super(modid, "abandoned_farmland");
        setTreeDensity(1.785f);
        register(Trees.silver_birch, 0.125f, 0.25f);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0f, 1f,false);
        register(Floras.chives_lilac, FloraSpawnRule.occasional, 0f, 1f,false);
        register(Floras.leek, FloraSpawnRule.occasional, 0f, 1f,false);
        register(Floras.oak_leaf_cabbage, FloraSpawnRule.occasional, 0f, 1f,false);
        register(Floras.sagebush_white, FloraSpawnRule.rare, 0f, 1f,false);
        register(Structures.ruinedHouse);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
