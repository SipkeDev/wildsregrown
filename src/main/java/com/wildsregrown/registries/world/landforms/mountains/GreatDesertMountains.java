package com.wildsregrown.registries.world.landforms.mountains;

import com.sipke.NoiseGenerator;
import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class GreatDesertMountains extends Landform {

    public GreatDesertMountains() {
        super(modid, "great_desert_mountains", 0.92f, Placement.Elevation.mountain, Climate.hotDesert, Climate.coolDesert, Climate.coolShrubland, Climate.hotScrubland, Climate.steppe);
        register(MaterialRegistery.sandstone_brown, 8, 16, 5);
        register(MaterialRegistery.sand_yellow, 8, 16, 4);
        register(MaterialRegistery.sandstone_red, 3, 8, 2);
        register(MaterialRegistery.granite_red, 3, 8, 1);
        register(MaterialRegistery.granite_white, 3, 8, 1);
        register(MaterialRegistery.limestone_dark_grey, 32, 55, 5);
        register(MaterialRegistery.limestone_white, 3, 8, 1);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();
        Noise mountains = NoiseGenerator.cubic(seed.next(), 2222)
                .fbm(5, 1.65f, 0.6f, 0.38f)
                .curve(0.52f, 7f)
                .multiply(0.7f);

        Noise surface = NoiseGenerator.perlin(seed.next(), 1200)
                .ridged(5, 2.25f, 0.45f, 0.48f)
                .map(MapType.almostUnitIdentity, 0.315f, 0.92f)
                .multiply(0.3f);

        Noise erosion = NoiseGenerator.perlin(seed.next(), 64)
                .ridged(3, 2.25f, 0.45f, 0.48f)
                .multiply(0.022f);

        return mountains.add(surface)
                .subtract(erosion)
                .map(MapType.almostUnitIdentity, 0.08f, 0.96f)
                .warp(seed.next(), 512, 128)
                .warp(seed.next(), 128, 32);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 384)
                .pingpong(3);
    }

}
