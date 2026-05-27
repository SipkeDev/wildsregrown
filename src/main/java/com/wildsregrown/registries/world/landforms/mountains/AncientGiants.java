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

import static com.wildsregrown.WildsRegrown.modid;

public class AncientGiants extends Landform {

    public AncientGiants() {
        super(modid, "ancient_giants", 1f, Placement.Elevation.mountain, Climate.polarDesert, Climate.tundra, Climate.ice);
        register(MaterialRegistery.limestone_grey, 14, 26, 3);
        register(MaterialRegistery.sandstone_grey, 8, 14, 2);
        register(MaterialRegistery.slate_grey, 5, 10, 2);
        register(MaterialRegistery.slate_blue, 2, 6, 1);
        register(MaterialRegistery.marble_white, 2, 6, 1);
        register(MaterialRegistery.marble_black, 2, 6, 1);
        register(MaterialRegistery.marble_portoro, 2, 6, 1);
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
