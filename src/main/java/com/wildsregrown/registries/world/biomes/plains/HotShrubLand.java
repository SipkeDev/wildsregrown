package com.wildsregrown.registries.world.biomes.plains;

import com.sipke.Constant;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class HotShrubLand extends Biome {

    public HotShrubLand() {
        super(modid, "hot_shrubland");
        register(Floras.heather_red, FloraSpawnRule.occasional, 0, 1, true);
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return Constant.of(0);
    }

}
