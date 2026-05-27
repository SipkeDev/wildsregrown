package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

import static com.wildsregrown.WildsRegrown.modid;

public class GreatPlains extends Landform {

    public GreatPlains() {
        super(modid, "great_plains", 0.15f, Placement.Elevation.lowland, Climate.polarDesert, Climate.steppe, Climate.coolDesert, Climate.hotDesert, Climate.savanna, Climate.coniferousForest, Climate.deciduousForest, Climate.mixedForest, Climate.spiritForest);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        Noise selector = NoiseGenerator.simplex(seed.next(), 512)
                .fbm(3, 2.2f, 0.6f, 0.6f)
                .warp(seed.next(), 128, 64);

        Noise high = NoiseGenerator.cubic(seed.next(), 128)
                .fbm(3);

        Noise low = NoiseGenerator.simplex(seed.next(), 32)
                .multiply(0.25f);

        return selector.blend(high,low);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
