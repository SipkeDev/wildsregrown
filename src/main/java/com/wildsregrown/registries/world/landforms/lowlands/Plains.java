package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Plains extends Landform {

    public Plains() {
        super( 0.12f, Placement.Elevation.lowland, Climate.tundra, Climate.steppe, Climate.coolDesert, Climate.hotDesert, Climate.coniferousForest, Climate.savanna);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        Noise selector = NoiseGenerator.simplex(seed.next(), 320)
                .warp(seed.next(), 128, 64);

        Noise high = NoiseGenerator.cubic(seed.next(), 138)
                .fbm(3, 2.6f, 0.6f, 0.6f);

        Noise low = NoiseGenerator.simplex(seed.next(), 77)
                .fbm(3, 2.6f, 0.6f, 0.6f)
                .multiply(0.4f);

        return selector.blend(high,low);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
