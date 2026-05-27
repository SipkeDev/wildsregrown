package com.wildsregrown.registries.world.biomes.forest;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Structures;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class AncientForest extends Biome {

    public AncientForest() {
        super(modid, "ancient_forest");
        register(Trees.ancient_oak, 0.75f, 1);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0, 1,false);
        register(Floras.hydrangea_blue_mist, FloraSpawnRule.occasional, 0.75f, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
