package com.wildsregrown.registries.world.biomes.meadows.temperate;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

public class CampanulaMeadow extends Biome {

    public CampanulaMeadow() {
        setTreeDensity(1.57f);
        register(Trees.silver_birch, 0.25f, 0.75f);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.campanula_white, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.campanula_blue, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.campanula_lilac, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.campanula_pink, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.campanula_purple, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32);
    }

}
