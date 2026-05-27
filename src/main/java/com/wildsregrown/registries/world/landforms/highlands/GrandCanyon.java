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

import static com.wildsregrown.WildsRegrown.modid;

public class GrandCanyon extends Landform {

    public GrandCanyon() {
        super(modid, "grand_canyon", 0.5f, Placement.Elevation.highland, Climate.savanna, Climate.chaparral, Climate.hotScrubland);
        register(MaterialRegistery.sandstone_brown, 12, 22, 1);
        register(MaterialRegistery.sandstone_beige, 12, 22, 1);
        register(MaterialRegistery.sandstone_yellow, 2, 12, 6);
        register(MaterialRegistery.sandstone_white, 1, 4, 3);
        register(MaterialRegistery.sandstone_pink, 1, 4, 3);
        register(MaterialRegistery.sandstone_red, 1, 4, 3);
        register(MaterialRegistery.slate_purple, 1, 3, 1);
        register(MaterialRegistery.shale_red, 1, 2, 1);
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
