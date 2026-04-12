package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.Constant;
import com.sipke.NoiseGenerator;
import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.MaterialRegistery;

public class Ardennes extends Landform {

    public Ardennes() {
        super(0.5f, Placement.Elevation.highland, Climate.steppe, Climate.mixedForest, Climate.deciduousForest, Climate.coniferousForest);
        register(MaterialRegistery.limestone_grey.getKey(), 1, 24, 8);
        register(MaterialRegistery.slate_grey.getKey(), 1, 24, 5);
        register(MaterialRegistery.slate_purple.getKey(), 1, 5, 3);
        register(MaterialRegistery.slate_blue.getKey(), 1, 3, 2);
        register(MaterialRegistery.marble_green.getKey(), 1, 2, 1);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        Noise mask = NoiseGenerator.voronoi(seed.next(), 1024, Distance.hybrid, CellType.distance2Div, 0.8f, false)
                .warp(seed.next(), 384, 128)
                .warp(seed.next(), 128, 64)
                .invert()
                .map(MapType.hermite, 0f, 0.88f);

        Noise surface = NoiseGenerator.perlin(seed.next(), 1024)
                .fbm(5)
                .map(MapType.almostUnitIdentity, 0f, 0.92f)
                .scalebias(0.8f, 0.2f)
                .curve(8f);

        return surface.multiply(mask);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.simplex(iteration, 256)
                .pingpong(5)
                .invert();
    }

}
