package com.wildsregrown.registries.world.landforms.coast;

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

public class TinyCliffs extends Landform {

    public TinyCliffs() {
        super(modid, "tiny_cliffs", 0.24f, Placement.Elevation.coast, Climate.hotScrubland, Climate.coolShrubland, Climate.ice);
        register(MaterialRegistery.limestone_grey, 5, 10, 4);
        register(MaterialRegistery.limestone_beige, 8, 20, 2);
        register(MaterialRegistery.limestone_white, 1, 2, 1);
        register(MaterialRegistery.sandstone_grey, 5, 12, 1);
        register(MaterialRegistery.sandstone_brown, 4, 8, 1);
        register(MaterialRegistery.shale_red, 3, 8, 1);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();
       Noise mask = NoiseGenerator.simplex(seed.next(), 1400)
                .ridged(6, 1.85f, 0.5f, 0.5f)
                .map(MapType.hermite, 0.125f, 0.85f);

        return NoiseGenerator.perlin(seed.next(), 768)
                .fbm(3, 1.85f, 0.5f,0.5f)
                .warp(seed.next(), 128, 128)
                .warp(seed.next(), 64, 64)
                .multiply(mask.invert())
                .range(-0.125f, 1f);

    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
