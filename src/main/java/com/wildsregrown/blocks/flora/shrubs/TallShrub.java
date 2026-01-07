package com.wildsregrown.blocks.flora.shrubs;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import net.minecraft.block.BlockState;

import static com.wildsregrown.blocks.render.TintUtil.buildBlendMap;
import static com.wildsregrown.blocks.render.TintUtil.buildOverlayMap;

/**
 * Large model variant of shrubs.
 */
public class TallShrub extends Shrub {

    private final int[] rgb;

    public TallShrub(Settings settings, int leavesColor, int darkColor, int lightColor, int flowerColor, float toughness, int consumptionRate, int floweringRange) {
        super(settings, flowerColor, toughness, consumptionRate, floweringRange);
        this.rgb = buildBlendMap(leavesColor, darkColor, lightColor, moisture.getValues().size());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 1) {return this.getFlowerColor();}
        if (tintIndex == 0) {return rgb[MathUtil.clamp(state.get(moisture)-1, 0, rgb.length)];}
        return -1;
    }

}
