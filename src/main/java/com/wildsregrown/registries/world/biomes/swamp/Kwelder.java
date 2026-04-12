package com.wildsregrown.registries.world.biomes.swamp;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.biome.lakes.LakeFeature;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.Trees;
import com.wildsregrown.registries.world.identifiable.IdentifierBiome;

public class Kwelder extends IdentifierBiome {

    public Kwelder() {
        super("warm_swamp");
        setTreeDensity(12f);
        register(Floras.artiplex_shining, FloraSpawnRule.grouped, 0.75f, 1,false);
        register(Floras.artiplex_red, FloraSpawnRule.occasional, 0.75f, 1,false);
        register(Floras.artiplex_green, FloraSpawnRule.dotted, 0.75f, 1,false);
        register(Trees.silver_birch, 0.75f, 1);
        register(new LakeFeature(NoiseGenerator.simplex(getKey(), 768).fbm(3), 3));
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 128).fbm(3);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        return NoiseGenerator.constant(-1f);
    }

}
