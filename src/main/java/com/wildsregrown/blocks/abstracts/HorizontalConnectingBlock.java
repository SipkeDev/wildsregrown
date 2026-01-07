package com.wildsregrown.blocks.abstracts;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class HorizontalConnectingBlock extends Block implements Waterloggable {

    private static final EnumProperty<HorizontalConnected> SHAPE = ModProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public HorizontalConnectingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(SHAPE, HorizontalConnected.SINGLE).with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {

        if(isConnectingBlock(currentState)) {
            if (isConnectingBlock(validState) && currentState.get(FACING) == validState.get(FACING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a shape property based on the surroundings from the given blockstate and position
     */
    public HorizontalConnected getPartProperty(WorldView worldIn, BlockPos blockpos, Direction direction){

        BlockState state = worldIn.getBlockState(blockpos);
        BlockState stateLeft = worldIn.getBlockState(blockpos.offset(direction.rotateYClockwise()));
        BlockState stateRight = worldIn.getBlockState(blockpos.offset(direction.rotateYCounterclockwise()));

        boolean left = isConnectingBlock(stateLeft) && isValidFacing(state, stateLeft);
        boolean right = isConnectingBlock(stateRight) && isValidFacing(state, stateRight);

        if(left && right)
        {
            return HorizontalConnected.MIDDLE;
        }
        else if(left)
        {
            return HorizontalConnected.LEFT;
        }
        else if(right)
        {
            return HorizontalConnected.RIGHT;
        }

        return HorizontalConnected.SINGLE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        BlockState blockstate = getDefaultState().with(FACING, direction).with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);

        return blockstate.with(SHAPE, getPartProperty(context.getWorld(), blockpos, direction));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return state.with(SHAPE, getPartProperty(world, pos, state.get(FACING)));
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(SHAPE, FACING, Properties.WATERLOGGED);
    }

}