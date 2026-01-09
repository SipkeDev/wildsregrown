package com.wildsregrown.blocks.wood;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class OpenStairs extends StairsBlock implements ITintedBlock {

    private static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public OpenStairs(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, HALF, SHAPE, WATERLOGGED);
    }
}
