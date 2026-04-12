package com.wildsregrown.registries.world.landforms.coast;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

public class DesertBeach extends Landform {

    public DesertBeach() {
        super(0.175f, Placement.Elevation.coast, Climate.tundra, Climate.coolShrubland, Climate.hotScrubland, Climate.hotDesert, Climate.coolDesert);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return Constant.of(edge).map(MapType.quintic, 0.125f, 0.85f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
