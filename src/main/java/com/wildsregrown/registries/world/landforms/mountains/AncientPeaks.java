package com.wildsregrown.registries.world.landforms.mountains;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.wildsregrown.registries.world.MaterialRegistery;

public class AncientPeaks extends Landform {

    public AncientPeaks() {
        super(1f, Placement.Elevation.mountain, Climate.spiritForest, Climate.ancientForest);
        register(new StratumConfig(MaterialRegistery.marble_white.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_beige.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_black.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_portoro.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_green.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.marble_blue.getKey(), 2, 6, 1));
        register(new StratumConfig(MaterialRegistery.limestone_grey.getKey(), 5, 12, 1));
        register(new StratumConfig(MaterialRegistery.sandstone_grey.getKey(), 5, 12, 1));
        register(new StratumConfig(MaterialRegistery.slate_grey.getKey(), 5, 12, 1));
        register(new StratumConfig(MaterialRegistery.shale_grey.getKey(), 5, 12, 1));
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        return NoiseGenerator.perlin(seed.next(), 1512)
                .fbm(8, 1.8f, 0.5f, 0.5f);

    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 128);
    }

}
