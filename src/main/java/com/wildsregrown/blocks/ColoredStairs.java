package com.wildsregrown.blocks;

import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class ColoredStairs extends StairsBlock implements ITintedBlock {

    private final int rgb;

    public ColoredStairs(BlockState baseBlockState, Settings settings, int rgb) {
        super(baseBlockState, settings);
        this.rgb = rgb;
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return rgb;
    }

}
