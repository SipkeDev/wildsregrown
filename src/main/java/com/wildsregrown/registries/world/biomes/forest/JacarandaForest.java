package com.wildsregrown.registries.world.biomes.forest;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class JacarandaForest extends Biome {

    public JacarandaForest() {
        super(modid, "jacaranda_forest");
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.75f, 1,false);
        register(Trees.jacaranda, 0.75f, 1);
        register(Floras.hydrangea_blue_mist, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.hydrangea_blue, FloraSpawnRule.dotted, 0.75f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
