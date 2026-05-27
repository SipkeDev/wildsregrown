
package com.wildsregrown.blocks.decoration;

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
import wildsregrown.api.block.render.IRenderType;
import wildsregrown.api.block.render.ITintedBlock;

public class TentRoof extends Block implements SimpleWaterloggedBlock, ITintedBlock, IRenderType {

    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape NORTH;
    private static final VoxelShape WEST;
    private final int rgb;

    public TentRoof(Properties settings, int rgb) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
        this.rgb = rgb;
    }


    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();

        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER,pos));
        }
        return state;
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case SOUTH -> {return SOUTH;}
            case EAST  -> {return EAST ;}
            case NORTH -> {return NORTH;}
            case WEST  -> {return WEST ;}
            default -> {return NORTH;}
        }
    }

    static {
        SOUTH = Shapes.or(
                Block.box(0, 0, 0, 16, 1, 9),
                Block.box(0, -1, 0, 16, 0, 8),
                Block.box(0, -2, 1, 16, -1, 7),
                Block.box(0, -3, 2, 16, -2, 6),
                Block.box(0, 15, 15, 16, 16, 17),
                Block.box(0, -4, 3, 16, -3, 5),
                Block.box(0, 13, 13, 16, 14, 19),
                Block.box(0, 9, 9, 16, 10, 18),
                Block.box(0, 11, 11, 16, 12, 20),
                Block.box(0, 7, 7, 16, 8, 16),
                Block.box(0, 5, 5, 16, 6, 14),
                Block.box(0, 3, 3, 16, 4, 12),
                Block.box(0, 1, 1, 16, 2, 10),
                Block.box(0, 2, 2, 16, 3, 11),
                Block.box(0, 4, 4, 16, 5, 13),
                Block.box(0, 6, 6, 16, 7, 15),
                Block.box(0, 10, 10, 16, 11, 19),
                Block.box(0, 8, 8, 16, 9, 17),
                Block.box(0, 12, 12, 16, 13, 20),
                Block.box(0, 14, 14, 16, 15, 18)
        );
        NORTH = VoxelTransform.rotate180(SOUTH);
        EAST  = VoxelTransform.rotate270(SOUTH);
        WEST  = VoxelTransform.rotate90(SOUTH);
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return rgb;
    }
}