package com.wildsregrown.registries.world.landforms.coast;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Shallows extends Landform {

    public Shallows() {
        super(0.08f, Placement.Elevation.coast, Climate.tundra, Climate.coniferousForest, Climate.mixedForest, Climate.deciduousForest, Climate.steppe, Climate.polarDesert);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();
        return NoiseGenerator.perlin(seed.next(), 32)
                .fbm(3)
                .warp(seed.next(), 32, 32)
                .multiply(edge);

    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
