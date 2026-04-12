package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

public class GrandCanyon extends Landform {

    public GrandCanyon() {
        super(0.5f, Placement.Elevation.highland, Climate.savanna, Climate.chaparral, Climate.hotScrubland);
        register(new StratumConfig(MaterialRegistery.sandstone_brown.getKey(), 12, 22, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_beige.getKey(), 12, 22, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_yellow.getKey(), 2, 12, 6));
        register(new StratumConfig(MaterialRegistery.sandstone_white.getKey(), 1, 4, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_pink.getKey(), 1, 4, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_red.getKey(), 1, 4, 3));
        register(new StratumConfig(MaterialRegistery.slate_purple.getKey(), 1, 3, 1));
        register(new StratumConfig(MaterialRegistery.shale_red.getKey(), 1, 2, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        return NoiseGenerator.voronoi(seed.next(), 1800, Distance.chebyShev, CellType.distance2Div, 0.6f, false)
                .warp(seed.next(), 500, 500)
                .invert()
                .map(MapType.hermite, 0.15f, 0.65f)
                .steps(6,
                        NoiseGenerator.simplex(seed.next(), 512),
                        NoiseGenerator.simplex(seed.next(), 768).scalebias(0.25f, 0.25f),
                        NoiseGenerator.simplex(seed.next(), 512).scalebias(0.25f, 0.75f),
                        0.12f
                );
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
