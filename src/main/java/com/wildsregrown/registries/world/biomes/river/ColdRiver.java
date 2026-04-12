package com.wildsregrown.registries.world.biomes.river;

import com.sipke.api.features.flora.FloraSpawnRule;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.identifiable.IdentifierBiome;

public class ColdRiver extends IdentifierBiome {

    public ColdRiver() {
        super("frozen_river");
        register(Floras.thin_grass, FloraSpawnRule.full_coverage, 0.5f, 1,false);
        register(Floras.nettle, FloraSpawnRule.occasional, 0.5f, 1,false);
        register(Floras.chives_lilac, FloraSpawnRule.rare, 0.5f, 1,false);
    }

}
