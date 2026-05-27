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

public class WetAshForest extends Biome {

    public WetAshForest() {
        super(modid, "wet_ash_forest");
        register(Trees.ash, 0.25f, 1);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.75f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);
        register(Floras.nettle, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.foxglove_purple, FloraSpawnRule.rare, 0.75f, 1,false);
        register(Structures.small_ruins);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32).ridged(1);
    }

}
