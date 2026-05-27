package com.wildsregrown.registries.world.biomes.steppe;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class WetSageShrubPlains extends Biome {

    public WetSageShrubPlains() {
        super(modid, "wet_sage_shrub_plains");
        register(Floras.sagebush_purple, FloraSpawnRule.grouped, 0.75f, 1,true);
        register(Floras.thin_grass, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.sagebush_lilac, FloraSpawnRule.rare, 0.75f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128);
    }

}
