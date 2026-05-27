package com.wildsregrown.registries.world.biomes.grassfield.wet;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class WetCurlyGrassfield extends Biome {

    public WetCurlyGrassfield() {
        super(modid, "wet_curly_grassfield");
        register(Floras.curly_grass, FloraSpawnRule.full_coverage, 0f, 1,false);
        register(Floras.tall_curly_grass, FloraSpawnRule.dotted, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
