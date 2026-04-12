package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.NoiseGenerator;
import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.wildsregrown.registries.world.MaterialRegistery;

public class DesertRidges extends Landform {

    public DesertRidges() {
        super(0.65f, Placement.Elevation.highland, Climate.coolDesert, Climate.hotDesert);
        register(new StratumConfig(MaterialRegistery.sandstone_brown.getKey(), 12, 22, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_beige.getKey(), 12, 22, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_yellow.getKey(), 2, 8, 4));
        register(new StratumConfig(MaterialRegistery.sandstone_white.getKey(), 1, 2, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_pink.getKey(), 1, 1, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_red.getKey(), 1, 5, 3));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        Noise ridges = NoiseGenerator.perlin(seed.next(), 512)
                .ridged(5)
                .map(MapType.hermite,0f, 0.9f);

        Noise valleys = NoiseGenerator.simplex(seed.next(), 128)
                .multiply(0.25f);

        return NoiseGenerator.simplex(seed.next(), 384).fbm(3)
                .blend(ridges, valleys, 0.5f, 0.38f, MapType.hermite);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 768).fbm(5);
    }

}
