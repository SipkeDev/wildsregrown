package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class WorseLands extends Landform {

    public WorseLands() {
        super(modid, "worse_lands", 0.32f, Placement.Elevation.lowland, Climate.coolDesert, Climate.hotScrubland, Climate.coolShrubland, Climate.savanna, Climate.chaparral, Climate.steppe);
        register(MaterialRegistery.sandstone_brown, 8, 25, 4);
        register(MaterialRegistery.sandstone_beige, 2, 12, 3);
        register(MaterialRegistery.sandstone_yellow, 5, 25, 2);
        register(MaterialRegistery.sandstone_white, 1, 5, 1);
        register(MaterialRegistery.sandstone_red, 1, 5, 1);
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
