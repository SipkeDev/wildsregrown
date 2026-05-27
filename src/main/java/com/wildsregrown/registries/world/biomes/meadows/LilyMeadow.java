package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class LilyMeadow extends Biome {

    public LilyMeadow() {
        super(modid, "lily_meadow");
        register(Floras.grass, FloraSpawnRule.full_coverage, 0, 1,false);
        register(Floras.lily_white, FloraSpawnRule.grouped, 1, 1,true);
        register(Floras.lily_purple, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.lily_pink, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.lily_red, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.lily_yellow, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.lily_orange, FloraSpawnRule.occasional, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
