package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class DesertDunes extends Landform {

    public DesertDunes() {
        super(0.25f, Placement.Elevation.lowland, Climate.hotDesert, Climate.coolDesert);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();

        return NoiseGenerator.simplex(seed.next(), 512)
                .fbm(3, 1.35f, 0.5f, 0.5f)
                .warp(seed.next(), 256, 256)
                .warp(seed.next(), 128, 128)
                .warp(seed.next(), 64, 64);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
