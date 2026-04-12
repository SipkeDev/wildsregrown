package com.wildsregrown.registries.world.landforms.coast;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Dunes extends Landform {

    public Dunes() {
        super(0.18f, Placement.Elevation.coast, Climate.hotDesert, Climate.coolDesert, Climate.chaparral, Climate.deciduousForest, Climate.coniferousForest, Climate.mixedForest, Climate.tundra, Climate.savanna, Climate.mixedForest, Climate.steppe);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();

        return NoiseGenerator.voronoi(seed.next(), 512, Distance.hybrid, CellType.distance2Div, 0.5f, false).invert()
                .warp(seed.next(), 256, 256)
                .warp(seed.next(), 128, 128)
                .warp(seed.next(), 64, 64);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
