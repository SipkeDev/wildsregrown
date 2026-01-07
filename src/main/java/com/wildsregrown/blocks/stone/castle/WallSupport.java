package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.*;
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

public class WallSupport extends Block implements Waterloggable {

    private static final VoxelShape[] SHAPES;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public WallSupport(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, Properties.WATERLOGGED);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean waterlogged = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        Direction dir = ctx.getSide();
        if (dir.getAxis().isVertical()) {
            return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(Properties.WATERLOGGED, waterlogged);
        }
        return this.getDefaultState().with(FACING, dir.getOpposite()).with(Properties.WATERLOGGED, waterlogged);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            default    -> {return SHAPES[0];}
            case EAST  -> {return SHAPES[1];}
            case NORTH -> {return SHAPES[2];}
            case WEST  -> {return SHAPES[3];}
        }
    }
    static {
        VoxelShape shape = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0.375, 0.5, 0.6875, 1, 1),
                VoxelShapes.cuboid(0.375, 0, 0.625, 0.625, 0.375, 1)
        );
        SHAPES = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape),
        };
    }
}
