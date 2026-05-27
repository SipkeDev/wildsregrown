package com.wildsregrown.registries.world.biomes.meadows.dry;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class DryCloverMeadow extends Biome {

    public DryCloverMeadow() {
        super(modid, "dry_clover_meadow");
        setOvergrown(false);
        register(Floras.grass, FloraSpawnRule.grouped, 0.25f, 1,false);
        register(Floras.clover_white, FloraSpawnRule.occasional, 1, 1,true);
        register(Floras.clover_pink, FloraSpawnRule.rare, 1, 1,true);
        register(Floras.clover_red, FloraSpawnRule.rare, 1, 1,true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
