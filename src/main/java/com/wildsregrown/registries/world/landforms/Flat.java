package com.wildsregrown.registries.world.landforms;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.geology.StratumConfig;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.Constant;
import com.wildsregrown.registries.world.MaterialRegistery;

import static com.wildsregrown.WildsRegrown.modid;

public class Flat extends Landform {

    public Flat() {
        super(modid, "flat", 0f, Placement.Elevation.lowland, Climate.tundra, Climate.polarDesert, Climate.ice);
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
        return new Constant(0f);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return new Constant(1f);
    }
}
