package com.wildsregrown.registries.world.biomes.forest.pine.larch;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class RiverLarchForest extends Biome {

    public RiverLarchForest() {
        super(modid, "river_larch_forest");
        register(Floras.grass, FloraSpawnRule.full_coverage, 0.25f, 0.25f, false);
        register(Floras.bluebottle_dark, FloraSpawnRule.occasional, 1, 1, true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
