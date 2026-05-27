package com.wildsregrown.registries.world.landforms.sea;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

import static com.wildsregrown.WildsRegrown.modid;

public class Ridge extends Landform {

    public Ridge() {
        super(modid, "ridge", 0.08f, Placement.Elevation.sea, Climate.coldSea, Climate.temperateSea, Climate.warmSea);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.cubic(seed.next(), 768)
                .ridged(2, 1.8f, 0.5f, 0.5f)
                .multiply(edge);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
