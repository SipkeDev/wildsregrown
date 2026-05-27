package com.wildsregrown.registries.world.biomes.meadows.temperate;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class HeatherMeadow extends Biome {

    public HeatherMeadow() {
        super(modid, "heather_meadow");
        register(Floras.heather_mauve, FloraSpawnRule.grouped, 0.25f, 1,true);
        register(Floras.heather_pink, FloraSpawnRule.dotted, 0.25f, 1,true);
        register(Floras.heather_lilac, FloraSpawnRule.occasional, 0.25f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
