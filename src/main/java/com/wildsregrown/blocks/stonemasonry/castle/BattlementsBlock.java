package com.wildsregrown.blocks.stonemasonry.castle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class BattlementsBlock extends Block {

    private static final VoxelShape SOUTH;
    private static final VoxelShape NORTH;
    private static final VoxelShape WEST;
    private static final VoxelShape EAST;

    public BattlementsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        switch (direction) {
            case NORTH -> {return NORTH;}
            case EAST  -> {return EAST;}
            case SOUTH -> {return SOUTH;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
    }

    //N/E/S/W
    static {
        NORTH = VoxelTransform.rotate180(Shapes.or(
                Shapes.box(0, 0.0625, 0.125, 1, 0.125, 0.5),
                Shapes.box(0, 0, 0.0625, 1, 0.0625, 0.5625),
                Shapes.box(0, 0.125, 0.1875, 1, 0.1875, 0.4375),
                Shapes.box(0, 0.1875, 0.25, 1, 0.25, 0.375)
                )
        );
        EAST = VoxelTransform.rotate90(NORTH);
        SOUTH = VoxelTransform.rotate180(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }

}
