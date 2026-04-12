package com.wildsregrown.registries.world.landforms;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

public class Flat extends Landform {

    public Flat() {
        super(0f, Placement.Elevation.lowland, Climate.tundra, Climate.polarDesert, Climate.ice);
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
        return new Constant(0f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return new Constant(1f);
    }
}
