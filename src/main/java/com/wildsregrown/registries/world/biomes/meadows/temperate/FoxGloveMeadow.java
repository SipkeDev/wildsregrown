package com.wildsregrown.registries.world.biomes.meadows.temperate;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class FoxGloveMeadow extends Biome {

    public FoxGloveMeadow() {
        super(modid, "foxglove_meadow");
        setTreeDensity(1.34f);
        register(Trees.silver_birch, 0.25f, 0.75f);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.foxglove_white, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.foxglove_pink, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.foxglove_purple, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.foxglove_red, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.foxglove_yellow, FloraSpawnRule.occasional, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 32);
    }

}
