package com.wildsregrown.registries.world.landforms.lowlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;
import com.sipke.Constant;

import static com.wildsregrown.WildsRegrown.modid;

public class MysticPlains extends Landform {

    public MysticPlains() {
        super(modid, "mystic_plains", 0.14f, Placement.Elevation.lowland, Climate.spiritForest, Climate.ancientForest);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        return NoiseGenerator.perlin(seed.next(), 384).fbm(5)
                .multiply(Constant.of(edge).map(0f, 0.38f));
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
