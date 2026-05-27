
package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.shapes.Door;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenDoor extends Door implements SimpleWaterloggedBlock, ITintedBlock {

    public WoodenDoor(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(ModProperties.LINSEED_PAINT, BlockStateProperties.OPEN, PART, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(ModProperties.LINSEED_PAINT).getRGB();
    }

}