package com.wildsregrown.blocks.stonemasonry.castle;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.VerticalConnected;

public class Pillar extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] WEST;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] SINGLE;

    //Defining Default BlockState
    public Pillar(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN).setValue(WRGProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).setValue(ModProperties.VARIATIONS_2, 1).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BlockStateProperties.FACING_HOPPER, WRGProperties.VERTICAL_CONNECTED, BlockStateProperties.WATERLOGGED, ModProperties.VARIATIONS_2);
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {
        return currentState.getBlock() instanceof Pillar && validState.getBlock() instanceof Pillar && currentState.getValue(BlockStateProperties.FACING_HOPPER) == validState.getValue(BlockStateProperties.FACING_HOPPER);
    }

    /**
     * Returns a shape property based from the given blockstate and position
     */
    public VerticalConnected getPartProperty(BlockState state, LevelReader worldIn, BlockPos blockpos) {
        BlockState stateUp = worldIn.getBlockState(blockpos.above());
        BlockState stateDown = worldIn.getBlockState(blockpos.below());

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
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getClickedFace();
        if (direction.getAxis().isVertical()) {
            direction = Direction.UP;
        }
        boolean sneak = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();

        BlockState blockstate = defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, direction.getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER).setValue(ModProperties.VARIATIONS_2, sneak ? 2 : 1);
        return blockstate.setValue(WRGProperties.VERTICAL_CONNECTED, getPartProperty(blockstate, context.getLevel(), blockpos));
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER, pos));
        }
        return direction.getAxis().isVertical() && neighborState.getBlock() instanceof Pillar ? state.setValue(WRGProperties.VERTICAL_CONNECTED, getPartProperty(state, world, pos)) : super.updateShape(state, world, tickView, pos, direction,  neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.FACING_HOPPER, rotation.rotate(state.getValue(BlockStateProperties.FACING_HOPPER)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.FACING_HOPPER)));
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        Direction direction = state.getValue(BlockStateProperties.FACING_HOPPER);
        int i = state.getValue(ModProperties.VARIATIONS_2) == 1 ? 0 : 4;

        switch (state.getValue(WRGProperties.VERTICAL_CONNECTED)) {
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
                Shapes.or(
                        Block.box(2, 5, 2, 14, 11, 14),
                        Block.box(1, 11, 1, 15, 12, 15),
                        Block.box(1, 4, 1, 15, 5, 15),
                        Block.box(0, 12, 0, 16, 16, 16),
                        Block.box(0, 0, 0, 16, 4, 16)),
                Shapes.or(
                        Block.box(1, 4, 1, 15, 8, 15),
                        Block.box(2, 8, 2, 14, 16, 14),
                        Block.box(0, 0, 0, 16, 4, 16)),
                Block.box(2, 0, 2, 14, 16, 14),
                Shapes.or(
                        Block.box(1, 8, 1, 15, 12, 15),
                        Block.box(2, 0, 2, 14, 8, 14),
                        Block.box(0, 12, 0, 16, 16, 16)),
                Shapes.or(
                        Block.box(2, 4, 2, 4, 14, 4),
                        Block.box(2, 4, 12, 4, 14, 14),
                        Block.box(12, 4, 2, 14, 14, 4),
                        Block.box(12, 4, 12, 14, 14, 14),
                        Block.box(3, 4, 3, 13, 14, 13),
                        Block.box(1, 14, 1, 15, 16, 15),
                        Block.box(0, 0, 0, 16, 1, 16),
                        Block.box(2, 1, 2, 14, 4, 14)),
                Shapes.or(
                        Block.box(0, 0, 0, 16, 2, 16),
                        Block.box(1, 5, 1, 15, 6, 15),
                        Block.box(2, 2, 2, 14, 5, 14),
                        Block.box(2, 6, 2, 4, 16, 4),
                        Block.box(2, 6, 12, 4, 16, 14),
                        Block.box(12, 6, 2, 14, 16, 4),
                        Block.box(12, 6, 12, 14, 16, 14),
                        Block.box(3, 6, 3, 13, 16, 13)),
                Shapes.or(
                        Block.box(2, 0, 2, 4, 16, 4),
                        Block.box(2, 0, 12, 4, 16, 14),
                        Block.box(12, 0, 2, 14, 16, 4),
                        Block.box(12, 0, 12, 14, 16, 14),
                        Block.box(3, 0, 3, 13, 16, 13)),
                Shapes.or(
                        Block.box(2, 10, 2, 14, 13, 14),
                        Block.box(12, 0, 2, 14, 10, 4),
                        Block.box(2, 0, 2, 4, 10, 4),
                        Block.box(2, 0, 12, 4, 10, 14),
                        Block.box(12, 0, 12, 14, 10, 14),
                        Block.box(3, 0, 3, 13, 10, 13),
                        Block.box(0, 13, 0, 16, 16, 16))
        };
        SOUTH = new VoxelShape[] {
                Shapes.or(
                        Block.box(2, 5, 10, 14, 11, 16),
                        Block.box(1, 4, 9, 15, 5, 16),
                        Block.box(1, 11, 9, 15, 12, 16),
                        Block.box(0, 12, 8, 16, 16, 16),
                        Block.box(0, 0, 8, 16, 4, 16)),
                Shapes.or(
                        Block.box(1, 4, 9, 15, 8, 16),
                        Block.box(0, 0, 8, 16, 4, 16),
                        Block.box(2, 8, 10, 14, 16, 16)),
                Block.box(2, 0, 10, 14, 16, 16),
                Shapes.or(
                        Block.box(1, 8, 9, 15, 12, 16),
                        Block.box(2, 0, 10, 14, 8, 16),
                        Block.box(0, 12, 8, 16, 16, 16)),
                Shapes.or(
                        Block.box(2, 4, 10, 4, 14, 12),
                        Block.box(12, 4, 10, 14, 14, 12),
                        Block.box(3, 4, 11, 13, 14, 16),
                        Block.box(1, 14, 9, 15, 16, 16),
                        Block.box(0, 0, 8, 16, 1, 16),
                        Block.box(2, 1, 10, 14, 4, 16)),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 2, 16),
                        Block.box(1, 5, 9, 15, 6, 16),
                        Block.box(2, 2, 10, 14, 5, 16),
                        Block.box(2, 6, 10, 4, 16, 12),
                        Block.box(12, 6, 10, 14, 16, 12),
                        Block.box(3, 6, 11, 13, 16, 16)),
                Shapes.or(
                        Block.box(2, 0, 10, 4, 16, 12),
                        Block.box(12, 0, 10, 14, 16, 12),
                        Block.box(3, 0, 11, 13, 16, 16)),
                Shapes.or(
                        Block.box(2, 10, 10, 14, 13, 16),
                        Block.box(12, 0, 10, 14, 10, 12),
                        Block.box(2, 0, 10, 4, 10, 12),
                        Block.box(3, 0, 11, 13, 10, 16),
                        Block.box(0, 13, 8, 16, 16, 16))
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