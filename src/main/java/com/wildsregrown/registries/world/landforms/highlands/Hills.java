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

import static com.wildsregrown.WildsRegrown.modid;

public class Hills extends Landform {

    public Hills() {
        super(modid, "hills", 0.5f, Placement.Elevation.highland, Climate.tundra, Climate.coniferousForest, Climate.steppe, Climate.mixedForest, Climate.chaparral, Climate.coolShrubland, Climate.hotScrubland);
        register(MaterialRegistery.sandstone_grey, 8, 16, 3);
        register(MaterialRegistery.sandstone_beige, 8, 16, 3);
        register(MaterialRegistery.sandstone_brown, 8, 16, 3);
        register(MaterialRegistery.slate_grey, 4, 8, 2);
        register(MaterialRegistery.slate_green, 2, 4, 1);
        register(MaterialRegistery.slate_red, 2, 4, 1);
        register(MaterialRegistery.slate_purple, 2, 4, 1);
        register(MaterialRegistery.limestone_grey, 24, 42, 8);
        register(MaterialRegistery.limestone_dark_grey, 32, 55, 5);
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
