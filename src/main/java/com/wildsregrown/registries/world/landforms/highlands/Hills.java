package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.Constant;
import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class Hills extends Landform {

    public Hills() {
        super(0.5f, Placement.Elevation.highland, Climate.tundra, Climate.coniferousForest, Climate.steppe, Climate.mixedForest, Climate.chaparral, Climate.coolShrubland, Climate.hotScrubland);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 1200)
                .fbm(3, 3.8f, 0.5f, 0.5f)
                .map(MapType.hermite, 0.1f, 0.9f)
                ;
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
