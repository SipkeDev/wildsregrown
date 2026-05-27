package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class CoolShrubLand extends Biome {

    public CoolShrubLand() {
        super(modid, "cool_shrubland");
        register(Floras.heather_mauve, FloraSpawnRule.occasional, 0, 1, true);
        register(Floras.chives_pink, FloraSpawnRule.rare, 0.5f, 1,false);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }
}
