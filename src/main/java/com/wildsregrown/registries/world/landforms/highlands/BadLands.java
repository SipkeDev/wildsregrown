package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

public class BadLands extends Landform {

    public BadLands() {
        super(0.45f, Placement.Elevation.highland, Climate.coolDesert, Climate.hotScrubland, Climate.coolShrubland, Climate.savanna, Climate.chaparral, Climate.steppe);
        register(new StratumConfig(MaterialRegistery.sandstone_yellow.getKey(), 8, 25, 4));
        register(new StratumConfig(MaterialRegistery.sandstone_white.getKey(), 2, 12, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_red.getKey(), 5, 25, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.perlin(seed.next(), 768)
                .warp(seed.next(), 250, 250)
                .ridged(2)
                .fbm(5, 2.5f, 0.5f, 0.5f)
                .steps(12,
                        NoiseGenerator.cubic(seed.next(), 250),
                        NoiseGenerator.simplex(seed.next(), 500).multiply(0.25f),
                        NoiseGenerator.simplex(seed.next(), 250).scalebias(0.5f, 0.5f),
                        0.35f
                );
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
