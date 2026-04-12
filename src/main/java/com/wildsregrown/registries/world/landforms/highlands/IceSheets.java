package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

public class IceSheets extends Landform {

    public IceSheets() {
        super(0.5f, Placement.Elevation.highland, Climate.ice);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();
        return NoiseGenerator.voronoi(seed.next(), 512, Distance.hybrid, CellType.cellValue, 0.8f, false);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
