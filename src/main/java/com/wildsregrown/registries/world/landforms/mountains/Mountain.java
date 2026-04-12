package com.wildsregrown.registries.world.landforms.mountains;

import com.sipke.Constant;
import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.api.geology.StratumConfig;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

public class Mountain extends Landform {

    public Mountain() {
        super(1f, Placement.Elevation.mountain, Climate.chaparral, Climate.coniferousForest, Climate.polarDesert, Climate.tundra);
        register(new StratumConfig(MaterialRegistery.limestone_grey.getKey(), 5, 34,5));
        register(new StratumConfig(MaterialRegistery.limestone_beige.getKey(), 5, 13, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_grey.getKey(), 5, 13, 2));
        register(new StratumConfig(MaterialRegistery.sandstone_beige.getKey(), 5, 8, 2));
        register(new StratumConfig(MaterialRegistery.slate_grey.getKey(), 3, 5, 1));
        register(new StratumConfig(MaterialRegistery.shale_grey.getKey(), 5, 8, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {

        seed.reset();

        Noise erosion = NoiseGenerator.perlin(seed.next(), 768)
                .pingpong(3, 2.25f, 0.5f, 0.5f, 2)
                .multiply(0.12f);

        Noise surface = NoiseGenerator.perlin(seed.next(), 384)
                .fbm(3, 3.5f, 0.5f, 0.5f)
                .multiply(0.12f);

        return NoiseGenerator.perlin(seed.next(), 2200).fbm(5)
                .scalebias(0.6f, 0.4f)
                .subtract(erosion)
                .multiply(0.8f)
                .add(surface)
                .multiply(Constant.of(edge).map(MapType.almostUnitIdentity, 0.002f, 0.43f));
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 128);
    }

}
