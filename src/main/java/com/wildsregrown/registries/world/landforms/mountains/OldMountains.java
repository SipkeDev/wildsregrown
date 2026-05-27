package com.wildsregrown.registries.world.landforms.mountains;

import com.sipke.Constant;
import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class OldMountains extends Landform {

    public OldMountains() {
        super(modid, "old_mountains", 0.85f, Placement.Elevation.mountain, Climate.mixedForest, Climate.coniferousForest, Climate.deciduousForest, Climate.ancientForest, Climate.spiritForest, Climate.savanna);
        register(MaterialRegistery.sandstone_grey, 5, 21, 4);
        register(MaterialRegistery.sandstone_beige, 5, 8, 2);
        register(MaterialRegistery.sandstone_brown, 5, 8, 2);
        register(MaterialRegistery.limestone_grey, 13, 55, 2);
        register(MaterialRegistery.marble_black, 5, 13, 2);
        register(MaterialRegistery.marble_white, 5, 13, 2);
        register(MaterialRegistery.slate_grey, 5, 8, 1);
        register(MaterialRegistery.shale_grey, 5, 8, 1);
        register(MaterialRegistery.shale_red, 5, 8, 1);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.perlin(seed.next(), 2048)
                .fbm(5, 1.8f, 0.5f, 0.5f)
                .map(MapType.almostUnitIdentity, 0.15f, 0.9f)
                .steps(12,
                        NoiseGenerator.simplex(seed.next(), 256).multiply(0.5f),
                        NoiseGenerator.simplex(seed.next(),128).scalebias(0.3f, 0.15f),
                        NoiseGenerator.simplex(seed.next(),64).scalebias(0.15f, 0.5f),
                        0.1f
                );
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
