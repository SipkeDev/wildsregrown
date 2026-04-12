package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

public class OakMeadow extends Biome {

    public OakMeadow() {
        setTreeDensity(1.3f);
        register(Trees.oak, 0f ,1f);
        register(Floras.grass, FloraSpawnRule.full_coverage, 0, 1,false);
        register(Floras.daisy, FloraSpawnRule.dotted, 1, 1,true);
        register(Floras.bluebottle_light, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.cornflower, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.clover_white, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.marigold, FloraSpawnRule.rare, 1, 1,true);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
