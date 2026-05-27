package com.wildsregrown.blocks.flora;

import com.sipke.math.MathUtil;
import net.minecraft.world.level.block.state.BlockState;
import wildsregrown.api.block.flora.type.ShrubFlora;
import wildsregrown.api.block.render.TintUtil;

/**
 * Large model variant of shrubs.
 */
public class TallShrubFlora extends ShrubFlora {

    private final int[] rgb;

    public TallShrubFlora(Properties settings, int leavesColor, int darkColor, int lightColor, int flowerColor, float toughness, int consumptionRate, int floweringRange) {
        super(settings, flowerColor, toughness, consumptionRate, floweringRange);
        this.rgb = TintUtil.buildBlendMap(leavesColor, darkColor, lightColor, moisture.getPossibleValues().size());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 1) {return this.getFlowerColor();}
        if (tintIndex == 0) {return rgb[MathUtil.clamp(state.getValue(moisture)-1, 0, rgb.length)];}
        return -1;
    }

}
