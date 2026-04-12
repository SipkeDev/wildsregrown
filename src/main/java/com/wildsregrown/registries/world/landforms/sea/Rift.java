package com.wildsregrown.registries.world.landforms.sea;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Rift extends Landform {

    public Rift() {
        super(0.125f, Placement.Elevation.sea, Climate.coldSea, Climate.temperateSea, Climate.warmSea);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.cubic(seed.next(), 768)
                .ridged(2, 1.8f, 0.5f, 0.5f)
                .map(MapType.inverseQuintic, 0.5f, 0.85f)
                .invert()
                .multiply(edge);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
