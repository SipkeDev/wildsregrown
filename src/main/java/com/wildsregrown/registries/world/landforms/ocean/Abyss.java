package com.wildsregrown.registries.world.landforms.ocean;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Abyss extends Landform {

    public Abyss() {
        super(32, Placement.Elevation.ocean, Climate.coldOcean, Climate.temperateOcean, Climate.warmOcean);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.cubic(seed.next(), 1024)
                .fbm(3)
                .warp(seed.next(), 250, 250)
                .map(MapType.inverseQuintic, 0.25f, 0.38f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
