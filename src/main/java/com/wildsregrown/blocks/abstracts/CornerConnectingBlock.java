package com.wildsregrown.blocks.abstracts;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.StairShape;
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
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class CornerConnectingBlock extends Block implements Waterloggable {

    private static final EnumProperty<StairShape> SHAPE = Properties.STAIR_SHAPE;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public CornerConnectingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(SHAPE, StairShape.STRAIGHT).with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(FACING);
        StairShape stairShape = state.get(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    switch (stairShape) {
                        case INNER_LEFT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        }
                        case INNER_RIGHT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        }
                        case OUTER_LEFT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        }
                        default -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180);
                        }
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    switch (stairShape) {
                        case INNER_LEFT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        }
                        case INNER_RIGHT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        }
                        case OUTER_LEFT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        }
                        case STRAIGHT -> {
                            return state.rotate(BlockRotation.CLOCKWISE_180);
                        }
                    }
                }
        }

        return super.mirror(state, mirror);
    }

    /**
     * Checks corners first, then same facing.
     * @param state, current state of this block.
     * @param world, MineCraft world
     * @param pos, current position of this block.
     * @return EnumProperty
     */
    public StairShape getPartProperty(BlockState state, WorldView world, BlockPos pos){

        Direction direction = state.get(FACING);

        //Check outer
        BlockState newState0 = world.getBlockState(pos.offset(direction));
        if (canConnectToBlock(newState0)) {
            Direction newDir0 = newState0.get(FACING);
            if (newDir0.getAxis() != state.get(FACING).getAxis() && isDifferentOrientation(state, world, pos, newDir0.getOpposite())) {
                if (newDir0 == direction.rotateYCounterclockwise()) {
                    return StairShape.OUTER_LEFT;
                }

                return StairShape.OUTER_RIGHT;
            }
        }
        //check inner
        BlockState newState1 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (canConnectToBlock(newState1)) {
            Direction newDir1 = newState1.get(FACING);
            if (newDir1.getAxis() != state.get(FACING).getAxis() && isDifferentOrientation(state, world, pos, newDir1)) {
                if (newDir1 == direction.rotateYCounterclockwise()) {
                    return StairShape.INNER_LEFT;
                }

                return StairShape.INNER_RIGHT;
            }
        }

        return StairShape.STRAIGHT;
    }
    protected abstract boolean canConnectToBlock(BlockState blockState);
    private boolean isDifferentOrientation(BlockState state, WorldView world, BlockPos pos, Direction direction) {
        BlockState newState = world.getBlockState(pos.offset(direction));
        return !canConnectToBlock(newState) || newState.get(FACING) != state.get(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        BlockState blockState = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return blockState.with(SHAPE, getPartProperty(blockState, ctx.getWorld(), blockPos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return direction.getAxis().isHorizontal() ? state.with(SHAPE, getPartProperty(state, world, pos)) : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(SHAPE, FACING, Properties.WATERLOGGED);
    }

}