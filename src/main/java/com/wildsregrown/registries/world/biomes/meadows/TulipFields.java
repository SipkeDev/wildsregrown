package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class TulipFields extends Biome {

    public TulipFields() {
        super(modid, "tulip_fields");
        register(Floras.tulip_red, FloraSpawnRule.full_coverage, 1, 1, true);
        register(Floras.tulip_white, FloraSpawnRule.dotted, 1, 1, true);
        register(Floras.tulip_orange, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_pink, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_yellow, FloraSpawnRule.occasional, 1, 1, true);
        register(Floras.tulip_purple, FloraSpawnRule.rare, 1, 1, true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}