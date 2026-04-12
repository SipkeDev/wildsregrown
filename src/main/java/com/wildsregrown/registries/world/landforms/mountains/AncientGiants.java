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

public class AncientGiants extends Landform {

    public AncientGiants() {
        super(1f, Placement.Elevation.mountain, Climate.polarDesert, Climate.tundra, Climate.ice);
        register(new StratumConfig(MaterialRegistery.limestone_grey.getKey(), 14, 26, 3));
        register(new StratumConfig(MaterialRegistery.sandstone_grey.getKey(), 8, 14, 2));
        register(new StratumConfig(MaterialRegistery.slate_grey.getKey(), 5, 10, 2));
        register(new StratumConfig(MaterialRegistery.slate_blue.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_white.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_black.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_portoro.getKey(), 2, 6, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        return NoiseGenerator.perlin(seed.next(), 1512)
                .ridged(8, 1.85f, 0.5f, 0.5f)
                .scalebias(0.5f, 0.5f)
                .terrace(12,
                        NoiseGenerator.simplex(seed.next(), 384).map(MapType.hermite, 0.38f, 0.62f),
                        NoiseGenerator.simplex(seed.next(),256).scalebias(0.15f,0.15f),
                        NoiseGenerator.simplex(seed.next(),128).scalebias(0.3f, 0.5f),
                        0.08f
                )
                .steps(24,
                        NoiseGenerator.simplex(seed.next(), 512).map(MapType.hermite, 0.15f, 0.32f)
                )
                ;

    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 128);
    }

}
