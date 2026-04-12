package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class SpireaMeadow extends Biome {

    public SpireaMeadow() {
        register(Floras.spirea_green_mauve, FloraSpawnRule.dotted, 0.25f, 1,true);
        register(Floras.spirea_green_red, FloraSpawnRule.dotted, 0.25f, 1,true);
        register(Floras.spirea_green_white, FloraSpawnRule.dotted, 0.25f, 1,true);
        register(Floras.spirea_green_pink, FloraSpawnRule.dotted, 0.25f, 1,true);
        register(Floras.spirea_golden_white, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.spirea_golden_red, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.spirea_golden_mauve, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.spirea_golden_pink, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.spirea_blue_mauve, FloraSpawnRule.rare, 0.25f, 1,true);
        register(Floras.spirea_blue_red, FloraSpawnRule.rare, 0.25f, 1,true);
        register(Floras.spirea_blue_white, FloraSpawnRule.rare, 0.25f, 1,true);
        register(Floras.spirea_blue_pink, FloraSpawnRule.rare, 0.25f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
