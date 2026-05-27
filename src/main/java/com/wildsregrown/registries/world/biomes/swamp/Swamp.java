package com.wildsregrown.registries.world.biomes.swamp;

import com.sipke.api.features.biome.lakes.LakeFeature;
import com.sipke.api.features.botanic.flora.FloraSpawnRule;
import com.sipke.api.terrain.Biome;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.Floras;
import com.wildsregrown.registries.world.MaterialRegistery;
import com.wildsregrown.registries.world.Trees;

import static com.wildsregrown.WildsRegrown.modid;

public class Swamp extends Biome {

    public Swamp() {
        super(modid, "swamp");
        setSurface(MaterialRegistery.peat_brown, 2f);
        register(Floras.curly_grass, FloraSpawnRule.occasional, 0.25f, 1,true);
        register(Trees.bebb_willow, 1, 1);
        register(Trees.weeping_willow, 0.25f, 1);
        register(new LakeFeature(NoiseGenerator.simplex(getKey().hashCode(), 256).ridged(3).invert(), 6));
    }

    @Override
    protected Noise treeDensityNoise(int seed) {
        return NoiseGenerator.constant(0f);
    }

    @Override
    public Noise surfaceNoise(Seed seed, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 128).fbm(3).range(-0.5f, 1f);
    }

}
