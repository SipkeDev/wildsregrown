package com.wildsregrown.blocks.stonemasonry.castle;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class PeekingHole extends Block {

    private static final VoxelShape X;
    private static final VoxelShape Z;

    private static final EnumProperty<Direction.Axis> FACING = BlockStateProperties.HORIZONTAL_AXIS;
    private static final IntegerProperty VARS = ModProperties.VARIATIONS_2;

    public PeekingHole(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(VARS, 1).setValue(FACING, Direction.NORTH.getAxis()).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, VARS, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();
        return defaultBlockState().setValue(FACING, direction.getAxis()).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        Direction direction = rotation.rotate(state.getValue(FACING).getPositive());
        return state.setValue(FACING, direction.getAxis());
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(FACING)) {
            case X -> {return X;}
            case Z  -> {return Z;}
            default -> {return Shapes.block();}
        }
    }

    static {
        Z = Shapes.or(
                Shapes.box(0, 0, 0, 0.25, 1, 1),
                Shapes.box(0.75, 0, 0, 1, 1, 1),
                Shapes.box(0.25, 0.75, 0, 0.75, 1, 1)
        );
        X = VoxelTransform.rotate90(Z);
    }

}
