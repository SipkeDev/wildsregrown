package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PeekingHole extends Block {

    private static final VoxelShape X;
    private static final VoxelShape Z;

    private static final EnumProperty<Direction.Axis> FACING = Properties.HORIZONTAL_AXIS;
    private static final IntProperty VARS = ModProperties.VARIATIONS_2;

    public PeekingHole(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(VARS, 1).with(FACING, Direction.NORTH.getAxis()).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, VARS, Properties.WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        return getDefaultState().with(FACING, direction.getAxis()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        Direction direction = rotation.rotate(state.get(FACING).getPositiveDirection());
        return state.with(FACING, direction.getAxis());
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
            case X -> {return X;}
            case Z  -> {return Z;}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        Z = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 0.25, 1, 1),
                VoxelShapes.cuboid(0.75, 0, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.25, 0.75, 0, 0.75, 1, 1)
        );
        X = VoxelTransform.rotate90(Z);
    }

}
