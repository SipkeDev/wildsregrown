package com.wildsregrown.registries.world.biomes.grassfield.wet;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class WetTallGrassfield extends Biome {

    public WetTallGrassfield() {
        super(modid, "wet_tall_grassfield");
        register(Floras.tall_grass, FloraSpawnRule.full_coverage, 0f, 1,false);
        register(Floras.grass, FloraSpawnRule.occasional, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
