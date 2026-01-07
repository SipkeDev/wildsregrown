package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class Pillar extends Block implements Waterloggable {

    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] WEST;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] SINGLE;

    //Defining Default BlockState
    public Pillar(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HOPPER_FACING, Direction.DOWN).with(ModProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).with(ModProperties.VARIATIONS_2, 1).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(Properties.HOPPER_FACING, ModProperties.VERTICAL_CONNECTED, Properties.WATERLOGGED, ModProperties.VARIATIONS_2);
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {
        return currentState.getBlock() instanceof Pillar && validState.getBlock() instanceof Pillar && currentState.get(Properties.HOPPER_FACING) == validState.get(Properties.HOPPER_FACING);
    }

    /**
     * Returns a shape property based from the given blockstate and position
     */
    public VerticalConnected getPartProperty(BlockState state, WorldView worldIn, BlockPos blockpos) {
        BlockState stateUp = worldIn.getBlockState(blockpos.up());
        BlockState stateDown = worldIn.getBlockState(blockpos.down());

        boolean top = isValidFacing(state, stateDown);
        boolean bot = isValidFacing(state, stateUp);

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
        Direction direction = context.getSide();
        if (direction.getAxis().isVertical()) {
            direction = Direction.UP;
        }
        boolean sneak = context.getPlayer() != null && context.getPlayer().isSneaking();

        BlockState blockstate = getDefaultState().with(Properties.HOPPER_FACING, direction.getOpposite()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER).with(ModProperties.VARIATIONS_2, sneak ? 2 : 1);
        return blockstate.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(blockstate, context.getWorld(), blockpos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return direction.getAxis().isVertical() && neighborState.getBlock() instanceof Pillar ? state.with(ModProperties.VERTICAL_CONNECTED, getPartProperty(state, world, pos)) : super.getStateForNeighborUpdate(state, world, tickView, pos, direction,  neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HOPPER_FACING, rotation.rotate(state.get(Properties.HOPPER_FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HOPPER_FACING)));
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction direction = state.get(Properties.HOPPER_FACING);
        int i = state.get(ModProperties.VARIATIONS_2) == 1 ? 0 : 4;

        switch (state.get(ModProperties.VERTICAL_CONNECTED)) {
            case BOTTOM -> i += 1;
            case MIDDLE -> i += 2;
            case TOP    -> i += 3;
            default     -> {}
        }

        switch (direction) {
            case NORTH -> {return NORTH[i];}
            case EAST  -> {return EAST[i];}
            case SOUTH -> {return SOUTH[i];}
            case WEST  -> {return WEST[i];}
            default    -> {return SINGLE[i];}
        }
    }

    //Centre/N/E/S/W
    //Single/Bottom/Middle/Top
    static {
        SINGLE = new VoxelShape[]{
                VoxelShapes.union(
                        Block.createCuboidShape(2, 5, 2, 14, 11, 14),
                        Block.createCuboidShape(1, 11, 1, 15, 12, 15),
                        Block.createCuboidShape(1, 4, 1, 15, 5, 15),
                        Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                        Block.createCuboidShape(0, 0, 0, 16, 4, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(1, 4, 1, 15, 8, 15),
                        Block.createCuboidShape(2, 8, 2, 14, 16, 14),
                        Block.createCuboidShape(0, 0, 0, 16, 4, 16)),
                Block.createCuboidShape(2, 0, 2, 14, 16, 14),
                VoxelShapes.union(
                        Block.createCuboidShape(1, 8, 1, 15, 12, 15),
                        Block.createCuboidShape(2, 0, 2, 14, 8, 14),
                        Block.createCuboidShape(0, 12, 0, 16, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 4, 2, 4, 14, 4),
                        Block.createCuboidShape(2, 4, 12, 4, 14, 14),
                        Block.createCuboidShape(12, 4, 2, 14, 14, 4),
                        Block.createCuboidShape(12, 4, 12, 14, 14, 14),
                        Block.createCuboidShape(3, 4, 3, 13, 14, 13),
                        Block.createCuboidShape(1, 14, 1, 15, 16, 15),
                        Block.createCuboidShape(0, 0, 0, 16, 1, 16),
                        Block.createCuboidShape(2, 1, 2, 14, 4, 14)),
                VoxelShapes.union(
                        Block.createCuboidShape(0, 0, 0, 16, 2, 16),
                        Block.createCuboidShape(1, 5, 1, 15, 6, 15),
                        Block.createCuboidShape(2, 2, 2, 14, 5, 14),
                        Block.createCuboidShape(2, 6, 2, 4, 16, 4),
                        Block.createCuboidShape(2, 6, 12, 4, 16, 14),
                        Block.createCuboidShape(12, 6, 2, 14, 16, 4),
                        Block.createCuboidShape(12, 6, 12, 14, 16, 14),
                        Block.createCuboidShape(3, 6, 3, 13, 16, 13)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 0, 2, 4, 16, 4),
                        Block.createCuboidShape(2, 0, 12, 4, 16, 14),
                        Block.createCuboidShape(12, 0, 2, 14, 16, 4),
                        Block.createCuboidShape(12, 0, 12, 14, 16, 14),
                        Block.createCuboidShape(3, 0, 3, 13, 16, 13)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 10, 2, 14, 13, 14),
                        Block.createCuboidShape(12, 0, 2, 14, 10, 4),
                        Block.createCuboidShape(2, 0, 2, 4, 10, 4),
                        Block.createCuboidShape(2, 0, 12, 4, 10, 14),
                        Block.createCuboidShape(12, 0, 12, 14, 10, 14),
                        Block.createCuboidShape(3, 0, 3, 13, 10, 13),
                        Block.createCuboidShape(0, 13, 0, 16, 16, 16))
        };
        SOUTH = new VoxelShape[] {
                VoxelShapes.union(
                        Block.createCuboidShape(2, 5, 10, 14, 11, 16),
                        Block.createCuboidShape(1, 4, 9, 15, 5, 16),
                        Block.createCuboidShape(1, 11, 9, 15, 12, 16),
                        Block.createCuboidShape(0, 12, 8, 16, 16, 16),
                        Block.createCuboidShape(0, 0, 8, 16, 4, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(1, 4, 9, 15, 8, 16),
                        Block.createCuboidShape(0, 0, 8, 16, 4, 16),
                        Block.createCuboidShape(2, 8, 10, 14, 16, 16)),
                Block.createCuboidShape(2, 0, 10, 14, 16, 16),
                VoxelShapes.union(
                        Block.createCuboidShape(1, 8, 9, 15, 12, 16),
                        Block.createCuboidShape(2, 0, 10, 14, 8, 16),
                        Block.createCuboidShape(0, 12, 8, 16, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 4, 10, 4, 14, 12),
                        Block.createCuboidShape(12, 4, 10, 14, 14, 12),
                        Block.createCuboidShape(3, 4, 11, 13, 14, 16),
                        Block.createCuboidShape(1, 14, 9, 15, 16, 16),
                        Block.createCuboidShape(0, 0, 8, 16, 1, 16),
                        Block.createCuboidShape(2, 1, 10, 14, 4, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(0, 0, 8, 16, 2, 16),
                        Block.createCuboidShape(1, 5, 9, 15, 6, 16),
                        Block.createCuboidShape(2, 2, 10, 14, 5, 16),
                        Block.createCuboidShape(2, 6, 10, 4, 16, 12),
                        Block.createCuboidShape(12, 6, 10, 14, 16, 12),
                        Block.createCuboidShape(3, 6, 11, 13, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 0, 10, 4, 16, 12),
                        Block.createCuboidShape(12, 0, 10, 14, 16, 12),
                        Block.createCuboidShape(3, 0, 11, 13, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(2, 10, 10, 14, 13, 16),
                        Block.createCuboidShape(12, 0, 10, 14, 10, 12),
                        Block.createCuboidShape(2, 0, 10, 4, 10, 12),
                        Block.createCuboidShape(3, 0, 11, 13, 10, 16),
                        Block.createCuboidShape(0, 13, 8, 16, 16, 16))
        };
        EAST = new VoxelShape[] {
                VoxelTransform.rotate270(SOUTH[0]),
                VoxelTransform.rotate270(SOUTH[1]),
                VoxelTransform.rotate270(SOUTH[2]),
                VoxelTransform.rotate270(SOUTH[3]),
                VoxelTransform.rotate270(SOUTH[4]),
                VoxelTransform.rotate270(SOUTH[5]),
                VoxelTransform.rotate270(SOUTH[6]),
                VoxelTransform.rotate270(SOUTH[7])
        };
        NORTH = new VoxelShape[] {
                VoxelTransform.rotate180(SOUTH[0]),
                VoxelTransform.rotate180(SOUTH[1]),
                VoxelTransform.rotate180(SOUTH[2]),
                VoxelTransform.rotate180(SOUTH[3]),
                VoxelTransform.rotate180(SOUTH[4]),
                VoxelTransform.rotate180(SOUTH[5]),
                VoxelTransform.rotate180(SOUTH[6]),
                VoxelTransform.rotate180(SOUTH[7])
        };
        WEST = new VoxelShape[] {
                VoxelTransform.rotate90(SOUTH[0]),
                VoxelTransform.rotate90(SOUTH[1]),
                VoxelTransform.rotate90(SOUTH[2]),
                VoxelTransform.rotate90(SOUTH[3]),
                VoxelTransform.rotate90(SOUTH[4]),
                VoxelTransform.rotate90(SOUTH[5]),
                VoxelTransform.rotate90(SOUTH[6]),
                VoxelTransform.rotate90(SOUTH[7])
        };
    }
}