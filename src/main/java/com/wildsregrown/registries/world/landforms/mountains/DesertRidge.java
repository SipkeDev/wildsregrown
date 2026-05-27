package com.wildsregrown.registries.world.landforms.mountains;

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

public class DesertRidge extends Landform {

    public DesertRidge() {
        super(modid, "desert_ridge", 0.785f, Placement.Elevation.mountain, Climate.hotDesert, Climate.coolDesert, Climate.coolShrubland, Climate.hotScrubland);
        register(MaterialRegistery.sandstone_brown, 5, 10, 1);
        register(MaterialRegistery.sandstone_beige, 8, 12, 2);
        register(MaterialRegistery.sandstone_yellow, 3, 5, 8);
        register(MaterialRegistery.sandstone_red, 2, 6, 8);
        register(MaterialRegistery.sandstone_pink, 1, 3, 3);
        register(MaterialRegistery.limestone_beige, 12, 50, 4);
        register(MaterialRegistery.slate_grey, 5, 12, 1);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();

        Noise mask = NoiseGenerator.perlin(seed.next(),2200)
                .pingpong(5)
                .multiply(0.75f);

        Noise surface = NoiseGenerator.perlin(seed.next(), 768)
                .fbm(5)
                .map(0f, 0.95f);

        return mask.lerp(surface, 0.5f)
                .map(MapType.quintic, 0.185f, 0.92f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 128);
    }

}
