package com.wildsregrown.registries.world.landforms.ocean;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

import static com.wildsregrown.WildsRegrown.modid;

public class Basin extends Landform {

    public Basin() {
        super(modid, "basin", 0f, Placement.Elevation.ocean, Climate.coldOcean, Climate.temperateOcean, Climate.warmOcean);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return Constant.of(0f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
