package com.wildsregrown.registries.world.biomes.orchard;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class PlumOrchard extends Biome {

    public PlumOrchard() {
        super(modid, "plum_orchard");
        register(Trees.plum, 0.25f, 1);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);;
        register(Floras.tall_curly_grass, FloraSpawnRule.occasional, 0.25f, 1,false);

    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
