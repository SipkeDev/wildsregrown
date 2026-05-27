package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class Priaries extends Landform {

    public Priaries() {
        super(modid, "priaries", 0.125f, Placement.Elevation.lowland, Climate.coolShrubland, Climate.coniferousForest, Climate.steppe, Climate.mixedForest, Climate.chaparral, Climate.mixedForest);
        register(MaterialRegistery.sandstone_grey, 8, 16, 2);
        register(MaterialRegistery.sandstone_brown, 8, 16, 2);
        register(MaterialRegistery.limestone_grey, 24, 42, 5);
        register(MaterialRegistery.limestone_dark_grey, 32, 55, 3);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();

        Noise mask = NoiseGenerator.cubic(seed.next(), 384)
                .fbm(3)
                .warp(seed.next(), 256, 256);

        Noise flat = NoiseGenerator.simplex(seed.next(), 128)
                .warp(seed.next(), 32, 32)
                .multiply(0.125f);

        Noise hills = NoiseGenerator.cubic(seed.next(), 128)
                .fbm(5, 2.8f, 0.5f, 0.5f)
                .warp(seed.next(), 64, 32);

        return mask.blend(flat, hills, 0.65f, 0.45f, MapType.hermite);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
