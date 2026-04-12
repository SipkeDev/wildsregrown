package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class FlatLands extends Landform {

    public FlatLands() {
        super(0.2f, Placement.Elevation.lowland, Climate.hotDesert, Climate.coolDesert, Climate.ice, Climate.tundra, Climate.coniferousForest, Climate.savanna, Climate.steppe);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 1024)
                .fbm(3, 1.8f, 0.5f, 0.5f)
                .warp(seed.next(), 32, 32);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
