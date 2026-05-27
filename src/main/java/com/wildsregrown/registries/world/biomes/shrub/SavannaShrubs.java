package com.wildsregrown.registries.world.biomes.shrub;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class SavannaShrubs extends Biome {

    public SavannaShrubs() {
        super(modid, "savanna_shrubs");
        setTreeDensity(3f);
        register(Floras.spirea_green_red, FloraSpawnRule.grouped, 0.25f, 0.75f,true);
        register(Floras.spirea_golden_pink, FloraSpawnRule.dotted, 0.25f, 0.75f,true);
        register(Floras.spirea_golden_red, FloraSpawnRule.occasional, 0.25f, 0.75f,true);
        register(Trees.oak, 0.25f, 0.75f);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

}
