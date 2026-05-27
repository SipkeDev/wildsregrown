package com.wildsregrown.registries.world.biomes.meadows;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class BirchMeadow extends Biome {

    public BirchMeadow() {
        super(modid, "birch_meadow");
        setTreeDensity(8f);
        register(Trees.dwarf_birch, 0.5f, 1f);
        register(Floras.daisy, FloraSpawnRule.full_coverage, 1, 1, true);
        register(Floras.dandelion, FloraSpawnRule.grouped, 1, 1, true);
        register(Floras.cornflower, FloraSpawnRule.dotted, 1, 1, true);
        register(Floras.bluebottle_dark, FloraSpawnRule.rare, 1, 1, true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}