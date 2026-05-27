package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class Plateau extends Landform {

    public Plateau() {
        super(modid, "plateau", 0.33f, Placement.Elevation.highland, Climate.coolDesert, Climate.hotDesert, Climate.hotScrubland, Climate.savanna, Climate.chaparral, Climate.steppe);
        register(MaterialRegistery.sandstone_grey, 5, 10, 2);
        register(MaterialRegistery.sandstone_beige, 5, 10, 2);
        register(MaterialRegistery.limestone_beige, 5, 10, 2);
        register(MaterialRegistery.sandstone_brown, 5, 10, 2);
        register(MaterialRegistery.sandstone_yellow, 2, 4, 4);
        register(MaterialRegistery.sandstone_pink, 2, 4, 2);
        register(MaterialRegistery.sandstone_red, 2, 4, 5);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        Noise surface = NoiseGenerator.perlin(seed.next(), 256)
                .fbm(3);
        Noise valleys = NoiseGenerator.simplex(seed.next(), 1800)
                .ridged(3).invert()
                .map(MapType.quintic, 0.44f, 0.95f);
        Noise mask = Constant.of(edge).map(MapType.hermite, 0f, 0.5f);

        return valleys.lerp(surface, 0.22f).multiply(mask);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
