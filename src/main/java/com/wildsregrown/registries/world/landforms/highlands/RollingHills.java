package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

import static com.wildsregrown.WildsRegrown.modid;

public class RollingHills extends Landform {

    public RollingHills() {
        super(modid, "rolling_hills", 0.5f, Placement.Elevation.highland, Climate.tundra, Climate.coniferousForest, Climate.steppe, Climate.mixedForest, Climate.chaparral, Climate.coolShrubland, Climate.hotScrubland);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 768)
                .fbm(6, 1.8f, 0.5f, 0.5f)
                .warp(seed.next(), 384, 384)
                .map(MapType.hermite, 0.25f, 0.85f)
                ;
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
