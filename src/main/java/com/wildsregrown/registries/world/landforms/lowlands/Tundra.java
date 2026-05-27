package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

import static com.wildsregrown.WildsRegrown.modid;

public class Tundra extends Landform {

    public Tundra (){
        super(modid, "tundra", 0.12f, Placement.Elevation.lowland, Climate.tundra, Climate.ice, Climate.polarDesert);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.simplex(seed.next(), 768)
                .fbm(3, 3.5f, 0.5f, 0.5f)
                .warp(seed.next(), 256, 220)
                .warp(seed.next(), 180, 140)
                .warp(seed.next(), 80, 60);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
