package com.wildsregrown.registries.world.biomes.orchard;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class AppleOrchard extends Biome {

    public AppleOrchard() {
        super(modid, "apple_orchard");
        setTreeDensity(1.2f);
        register(Trees.apple, 0.25f, 1);
        register(Floras.curly_grass, FloraSpawnRule.dotted, 0.25f, 1,false);;
        register(Floras.tall_curly_grass, FloraSpawnRule.occasional, 0.25f, 1,false);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0f);
    }

}
