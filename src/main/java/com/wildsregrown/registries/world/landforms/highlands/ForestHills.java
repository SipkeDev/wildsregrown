package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

public class ForestHills extends Landform {

    public ForestHills() {
        super(0.35f, Placement.Elevation.highland, Climate.mixedForest, Climate.coniferousForest, Climate.deciduousForest, Climate.ancientForest, Climate.spiritForest);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.cubic(seed.next(), 256)
                .fbm(5)
                .warp(seed.next(), 384, 256)
                .terrace(8,
                        NoiseGenerator.simplex(seed.next(), 256)
                                .map(MapType.hermite, 0.38f, 0.62f),
                        Constant.of(0.2f),
                        Constant.of(0.6f),
                        0.07f)
                .steps(16, NoiseGenerator.simplex(seed.next(), 128).map(0.125f, 0.38f));
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
