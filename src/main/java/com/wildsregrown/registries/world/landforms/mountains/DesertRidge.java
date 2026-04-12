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

public class DesertRidge extends Landform {

    public DesertRidge() {
        super(0.785f, Placement.Elevation.mountain, Climate.hotDesert, Climate.coolDesert, Climate.coolShrubland, Climate.hotScrubland);
        register(new StratumConfig(MaterialRegistery.sandstone_brown.getKey(), 5, 10, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_beige.getKey(), 8, 12, 2));
        register(new StratumConfig(MaterialRegistery.sandstone_yellow.getKey(), 3, 5, 8));
        register(new StratumConfig(MaterialRegistery.sandstone_red.getKey(), 2, 6, 8));
        register(new StratumConfig(MaterialRegistery.sandstone_pink.getKey(), 1, 3, 3));
        register(new StratumConfig(MaterialRegistery.limestone_beige.getKey(), 12, 50, 4));
        register(new StratumConfig(MaterialRegistery.slate_grey.getKey(), 5, 12, 1));
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
