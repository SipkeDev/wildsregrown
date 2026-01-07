package com.wildsregrown.blocks;

import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.Direction;

public class ColoredLayered extends Layered implements Waterloggable, ITintedBlock {
    private final int rgb;

    public ColoredLayered(Settings settings, int rgb) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.DOWN).with(LAYERS, 8).with(WATERLOGGED, false));
        this.rgb = rgb;
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return rgb;
    }

}
