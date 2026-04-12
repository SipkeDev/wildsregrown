package com.wildsregrown.registries.world.landforms.mountains;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.api.geology.StratumConfig;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

public class Pinnacles extends Landform {

    public Pinnacles() {
        super(0.8f, Placement.Elevation.mountain, Climate.coolDesert, Climate.coolShrubland, Climate.steppe, Climate.tundra);
        register(new StratumConfig(MaterialRegistery.sandstone_grey.getKey(), 5, 10, 5));
        register(new StratumConfig(MaterialRegistery.sandstone_beige.getKey(), 2, 20, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_brown.getKey(), 1, 3, 1));
        register(new StratumConfig(MaterialRegistery.slate_grey.getKey(), 20, 40, 1));
        register(new StratumConfig(MaterialRegistery.slate_purple.getKey(), 1, 2, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        return NoiseGenerator.simplex(seed.next(), 2200)
                .fbm(5)
                .curve(0.5f, 6f)
                .terrace(5, NoiseGenerator.simplex(seed.next(), 256).map(MapType.hermite, 0.25f, 0.75f), 0.2f, 0.7f, 0.1f);

    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 128);
    }

}
