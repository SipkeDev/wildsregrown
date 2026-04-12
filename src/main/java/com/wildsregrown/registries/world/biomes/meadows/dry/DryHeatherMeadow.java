package com.wildsregrown.registries.world.biomes.meadows.dry;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

public class DryHeatherMeadow extends Biome {

    public DryHeatherMeadow() {
        register(Floras.heather_mauve, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.heather_silver, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.heather_pink, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Floras.heather_lilac, FloraSpawnRule.rare, 0.25f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
