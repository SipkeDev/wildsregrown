
package com.wildsregrown.blocks.decoration.pottery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class Urn extends Block implements Waterloggable {

    private static final VoxelShape SHAPE;

    public Urn(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        return getDefaultState().with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return state;
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static {
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.5, 0.75),
                VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 0.5625, 0.625),
                VoxelShapes.cuboid(0.4375, 0.5625, 0.4375, 0.5625, 0.625, 0.5625)
        );
    }

}