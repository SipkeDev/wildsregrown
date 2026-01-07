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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class VerticalConnectingBlock extends Block implements Waterloggable {

    public VerticalConnectingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ModProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    /**
     * Returns a shape property based from the given blockstate and position
     */
    public VerticalConnected getPartProperty(WorldView worldIn, BlockPos blockpos) {

        boolean top = isConnectingBlock(worldIn.getBlockState(blockpos.down()));
        boolean bot = isConnectingBlock(worldIn.getBlockState(blockpos.up()));

        if(top && bot)
        {
            return VerticalConnected.MIDDLE;
        }
        else if(top)
        {
            return VerticalConnected.TOP;
        }
        else if(bot)
        {
            return VerticalConnected.BOTTOM;
        }
        return VerticalConnected.SINGLE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        BlockState blockstate = getDefaultState().with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
        return blockstate.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(context.getWorld(), blockpos));
    }


    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return direction.getAxis().isVertical() ? state.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(world, pos)) : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(ModProperties.VERTICAL_CONNECTED, Properties.WATERLOGGED);
    }

}