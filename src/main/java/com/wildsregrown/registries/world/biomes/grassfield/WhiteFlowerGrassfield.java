package com.wildsregrown.registries.world.biomes.grassfield;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class WhiteFlowerGrassfield extends Biome {

    public WhiteFlowerGrassfield() {
        super(modid, "white_flower_grassfield");
        setTreeDensity(8f);
        register(Trees.oak, 0.5f, 1);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.5f, 1,true);
        register(Floras.dandelion, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.clover_white, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.spirea_green_white, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.lily_white, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 16);
    }

}
