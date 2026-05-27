package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class PoppyMeadow extends Biome {

    public PoppyMeadow() {
        super(modid, "poppy_meadow");
        register(Floras.poppy, FloraSpawnRule.full_coverage, 1, 1,true);
        register(Floras.grass, FloraSpawnRule.dotted, 0, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
