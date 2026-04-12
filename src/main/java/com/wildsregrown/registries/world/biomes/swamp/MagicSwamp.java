package com.wildsregrown.registries.world.biomes.swamp;

import com.sipke.NoiseGenerator;
import com.sipke.api.features.biome.lakes.LakeFeature;
import com.sipke.api.features.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.MaterialRegistery;
import com.wildsregrown.registries.world.Trees;
import com.wildsregrown.registries.world.identifiable.IdentifierBiome;

public class MagicSwamp extends IdentifierBiome {

    public MagicSwamp() {
        super("warm_swamp");
        setSurface(MaterialRegistery.peat_black, 2f);
        register(Floras.curly_grass, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Trees.bebb_willow, 0.5f, 1);
        register(Trees.weeping_willow, 0.25f, 1);
        register(Trees.glowing_willow, 0.25f, 1);
        register(new LakeFeature(NoiseGenerator.simplex(getKey(), 256).ridged(3).map(0.215f, 0.57f), 5));
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.simplex(seed, 80).ridged(3);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 128).fbm(3).range(-0.25f, 1f);
    }

}
