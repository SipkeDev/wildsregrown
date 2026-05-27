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
import com.sipke.noise2d.warp.GradientWarp;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class HighLands extends Landform {

    public HighLands() {
        super(modid, "highlands", 0.5f,Placement.Elevation.highland, Climate.tundra, Climate.steppe, Climate.polarDesert);
        register(MaterialRegistery.sandstone_grey, 8, 16, 2);
        register(MaterialRegistery.sandstone_brown, 8, 16, 2);
        register(MaterialRegistery.slate_grey, 4, 8, 1);
        register(MaterialRegistery.slate_green, 2, 4, 1);
        register(MaterialRegistery.limestone_grey, 24, 42, 5);
        register(MaterialRegistery.limestone_dark_grey, 32, 55, 3);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        Noise erosion = NoiseGenerator.perlin(seed.next(), 320)
                .ridged(3, 2.8f, 0.5f, 0.5f)
                .warp(new GradientWarp(seed.next(), 256), 128)
                .multiply(0.08f);

        return NoiseGenerator.perlin(seed.next(), 800)
                .steps(8,
                        NoiseGenerator.simplexFast(seed.next(), 500).map(0f, 0.4f))
                .warp(new GradientWarp(seed.next(), 1200), 512)
                .fbm(6, 2.2f, 0.6f, 0.5f)
                .warp(new GradientWarp(seed.next(), 512), 128)
                .subtract(erosion)
                .map(MapType.almostUnitIdentity, 0.1f, 0.9f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
