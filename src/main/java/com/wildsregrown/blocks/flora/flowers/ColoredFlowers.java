package com.wildsregrown.blocks.flora.flowers;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.render.TintUtil;
import net.minecraft.block.BlockState;

/**
 * Colored variant of FlowerFlora
 */
public class ColoredFlowers extends FlowerFlora {

    private final int flower;

    public ColoredFlowers(Settings settings, int rgb, int flower, float toughness, int consumptionRate, int floweringRange) {
        super(settings, rgb, toughness, consumptionRate, floweringRange);
        this.flower = flower;
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 0) {
            int base = rgb[MathUtil.clamp(state.get(moisture) - 1, 0, rgb.length)];
            if (FloraStage.isSick(state.get(stage))) {
                return TintUtil.blend(base, Colors.pastelPink, 0.75f);
            } else {
                return base;
            }
        }else if(tintIndex == 1){
            return flower;
        }else {
            return -1;
        }
    }

}
