package com.wildsregrown.blocks.abstracts;

import com.wildsregrown.blocks.properties.connecting.HorizontalCornerConnected;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class HorizontalCornerConnectingBlock extends Block implements Waterloggable {

    public HorizontalCornerConnectingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HorizontalCornerConnected.PART, HorizontalCornerConnected.SINGLE).with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState blockState) {
        return blockState.getBlock() instanceof HorizontalCornerConnectingBlock;
    }

    /**
     * Returns a blockstate
     */
    private boolean isSameFacing(BlockState currentState, WorldView worldIn, BlockPos blockPos, Direction direction) {
        BlockState newState = worldIn.getBlockState(blockPos.offset(direction));
        if (isConnectingBlock(currentState) && isConnectingBlock(newState)){
            return currentState.get(Properties.HORIZONTAL_FACING) == newState.get(Properties.HORIZONTAL_FACING);
        }
        return false;
    }
    private boolean isCorner(BlockState currentState, WorldView worldIn, BlockPos blockPos, Direction direction) {
        BlockState newState = worldIn.getBlockState(blockPos.offset(direction));
        return !isConnectingBlock(newState) || newState.get(Properties.HORIZONTAL_FACING) != currentState.get(Properties.HORIZONTAL_FACING);
    }
    private boolean isCornerState(WorldView worldIn, BlockPos blockPos, Direction direction) {
        BlockState newState = worldIn.getBlockState(blockPos.offset(direction));
        HorizontalCornerConnected part = newState.get(HorizontalCornerConnected.PART);
        return part == HorizontalCornerConnected.INNER_LEFT || part == HorizontalCornerConnected.OUTER_LEFT || part == HorizontalCornerConnected.INNER_RIGHT || part == HorizontalCornerConnected.OUTER_RIGHT;
    }

    /**
     * Checks corners first, then same facing.
     * @param state, current state of this block.
     * @param worldIn, MineCraft world
     * @param blockpos, current position of this block.
     * @return EnumProperty
     */
    public HorizontalCornerConnected getPartProperty(BlockState state, WorldView worldIn, BlockPos blockpos){

        Direction direction = state.get(Properties.HORIZONTAL_FACING);

        BlockState newState = worldIn.getBlockState(blockpos.offset(direction));
        if (isConnectingBlock(newState)){
            Direction newDir = newState.get(Properties.HORIZONTAL_FACING);
            if (newDir.getAxis() != (state.get(Properties.HORIZONTAL_FACING)).getAxis() && isCorner(state, worldIn, blockpos, newDir.getOpposite())) {
                if (newDir == direction.rotateYCounterclockwise()) {
                    return HorizontalCornerConnected.OUTER_LEFT;
                }
                return HorizontalCornerConnected.OUTER_RIGHT;
            }
        }

        BlockState newState1 = worldIn.getBlockState(blockpos.offset(direction.getOpposite()));
        if (isConnectingBlock(newState1)) {
            Direction newDir = newState1.get(Properties.HORIZONTAL_FACING);
            if (newDir.getAxis() != (state.get(Properties.HORIZONTAL_FACING)).getAxis() && isCorner(state, worldIn, blockpos, newDir)) {
                if (newDir == direction.rotateYCounterclockwise()) {
                    return HorizontalCornerConnected.INNER_LEFT;
                }

                return HorizontalCornerConnected.INNER_RIGHT;
            }
        }

        boolean left = isSameFacing(state, worldIn, blockpos, direction.rotateYClockwise());
        boolean right = isSameFacing(state, worldIn, blockpos, direction.rotateYCounterclockwise());

        if(left && right)
        {
            if (isCornerState(worldIn, blockpos, direction.rotateYClockwise())){
                return HorizontalCornerConnected.RIGHT;
            } else if (isCornerState(worldIn, blockpos, direction.rotateYCounterclockwise())){
                return HorizontalCornerConnected.LEFT;
            }else {
                return HorizontalCornerConnected.MIDDLE;
            }
        }
        else if(left)
        {
            return HorizontalCornerConnected.LEFT;
        }
        else if(right)
        {
            return HorizontalCornerConnected.RIGHT;
        }

        return HorizontalCornerConnected.SINGLE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        BlockState blockstate = getDefaultState().with(Properties.HORIZONTAL_FACING, direction).with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);

        return blockstate.with(HorizontalCornerConnected.PART, getPartProperty(blockstate, context.getWorld(), blockpos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return state.with(HorizontalCornerConnected.PART, getPartProperty(state, world, pos));
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(HorizontalCornerConnected.PART, Properties.HORIZONTAL_FACING, Properties.WATERLOGGED);
    }

}