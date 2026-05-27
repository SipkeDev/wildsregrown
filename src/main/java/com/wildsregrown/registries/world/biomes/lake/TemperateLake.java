package com.wildsregrown.registries.world.biomes.lake;

import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.wildsregrown.registries.world.Floras;

import static com.wildsregrown.WildsRegrown.modid;

public class TemperateLake extends Biome {

    public TemperateLake() {
        super(modid, "temperate_lake");
        register(Floras.thin_grass, FloraSpawnRule.dotted, 0.5f, 1,false);
        register(Floras.nettle, FloraSpawnRule.occasional, 0.5f, 1,false);
    }

}
