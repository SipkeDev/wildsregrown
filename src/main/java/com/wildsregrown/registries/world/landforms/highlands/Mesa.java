package com.wildsregrown.registries.world.landforms.highlands;

import com.sipke.api.categorization.Placement;
import com.sipke.api.categorization.Climate;
import com.sipke.api.terrain.Landform;
import com.sipke.core.Seed;
import com.sipke.math.MapType;
import com.sipke.noise2d.Noise;
import com.sipke.NoiseGenerator;

import static com.wildsregrown.WildsRegrown.modid;

public class Mesa extends Landform {

    public Mesa() {
        super(modid, "mesa", 0.5f, Placement.Elevation.highland, Climate.hotDesert, Climate.hotScrubland, Climate.savanna);
    }

    @Override
    public Noise elevation(Seed seed, float edge, float value) {
        seed.reset();

        Noise mask = NoiseGenerator.simplex(seed.next(), 850)
                .map(MapType.hermite, 0.25f, 0.5f);

        Noise underlayer = NoiseGenerator.simplex(seed.next(), 450)
                .fbm(3, 3.8f, 0.5f, 0.5f)
                .warp(seed.next(), 250, 250)
                .add(NoiseGenerator.simplex(seed.next(), 64).multiply(0.175f))
                .multiply(0.2f);

        Noise upperlayer = NoiseGenerator.simplex(seed.next(), 500)
                .warp(seed.next(), 180, 120)
                .ridged(3, 2.5f, 0.5f, 0.5f)
                .fbm(2)
                .boost(2);

        return mask.blend(underlayer, upperlayer, 0.25f, 1f, MapType.almostUnitIdentity);
    }

    @Override
    public Noise depthNoise(int iteration) {
        return NoiseGenerator.perlin(iteration, 256);
    }

}
