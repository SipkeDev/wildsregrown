
package com.wildsregrown.blocks.decoration.pottery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class Amphora extends Block implements Waterloggable {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;

    public Amphora(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.X).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        return getDefaultState().with(AXIS, context.getHorizontalPlayerFacing().getAxis()).with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return state;
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(AXIS, mirror.apply(state.get(AXIS).getPositiveDirection()).getAxis());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(AXIS, rotation.rotate(state.get(AXIS).getPositiveDirection()).getAxis());
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(AXIS, Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static {
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.5625, 0.6875),
                VoxelShapes.cuboid(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625)
        );
    }

}