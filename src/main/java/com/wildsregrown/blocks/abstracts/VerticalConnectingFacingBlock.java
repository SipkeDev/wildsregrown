package com.wildsregrown.blocks.abstracts;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class VerticalConnectingFacingBlock extends Block implements Waterloggable {

    public VerticalConnectingFacingBlock(Settings settings) {
        super(settings);
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {
        if(isConnectingBlock(currentState)) {
            if (isConnectingBlock(validState) && currentState.get(Properties.HORIZONTAL_FACING) == validState.get(Properties.HORIZONTAL_FACING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a shape property based from the given blockstate and position
     */
    public VerticalConnected getPartProperty(WorldView worldIn, BlockPos blockpos) {

        BlockState state = worldIn.getBlockState(blockpos);
        BlockState stateUp = worldIn.getBlockState(blockpos.up());
        BlockState stateDown = worldIn.getBlockState(blockpos.down());

        boolean top = isConnectingBlock(stateDown) && isValidFacing(state, stateDown);
        boolean bot = isConnectingBlock(stateUp) && isValidFacing(state, stateUp);

        if(isValidFacing(state, state)) {
            if (top && bot) {
                    return VerticalConnected.MIDDLE;
            } else if (top) {
                return VerticalConnected.TOP;
            } else if (bot) {
                return VerticalConnected.BOTTOM;
            }
        }

        return VerticalConnected.SINGLE;

    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);

        BlockState blockstate = getDefaultState().with(Properties.HORIZONTAL_FACING, context.getHorizontalPlayerFacing()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
        return blockstate.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(context.getWorld(), blockpos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return direction.getAxis().isVertical() ? state.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(world, pos)) : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(Properties.HORIZONTAL_FACING, ModProperties.VERTICAL_CONNECTED, Properties.WATERLOGGED);
    }
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }
}
