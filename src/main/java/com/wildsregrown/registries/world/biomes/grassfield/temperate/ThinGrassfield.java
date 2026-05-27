package com.wildsregrown.registries.world.biomes.grassfield.temperate;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class ThinGrassfield extends Biome {

    public ThinGrassfield() {
        super(modid, "thin_grassfield");
        register(Floras.thin_grass, FloraSpawnRule.grouped, 0f, 1,false);
        register(Floras.tall_thin_grass, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
