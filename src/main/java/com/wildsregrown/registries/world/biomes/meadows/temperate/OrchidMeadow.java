package com.wildsregrown.registries.world.biomes.meadows.temperate;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class OrchidMeadow extends Biome {

    public OrchidMeadow() {
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 1,false);
        register(Floras.orchid_white, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.orchid_yellow, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.orchid_orange, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.orchid_red, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.orchid_purple, FloraSpawnRule.rare, 1, 1,true);
        register(Floras.orchid_blue, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
