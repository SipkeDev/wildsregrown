package com.wildsregrown.blocks.carpentry;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import wildsregrown.api.block.render.ITintedBlock;

public class PaintedStairs extends StairBlock implements ITintedBlock {

    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public PaintedStairs(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

}
