package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class HydrangeaMeadow extends Biome {

    public HydrangeaMeadow() {
        register(Floras.grass, FloraSpawnRule.full_coverage, 0f, 1,false);
        register(Floras.hydrangea_white_mist, FloraSpawnRule.dotted, 0f, 1,true);
        register(Floras.hydrangea_white, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.hydrangea_lime, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.hydrangea_red, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.hydrangea_orange, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.hydrangea_pink, FloraSpawnRule.occasional, 0f, 1,true);
        register(Floras.hydrangea_violet, FloraSpawnRule.rare, 0f, 1,true);
        register(Floras.hydrangea_purple, FloraSpawnRule.rare, 0f, 1,true);
        register(Floras.hydrangea_blue_mist, FloraSpawnRule.rare, 0f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
