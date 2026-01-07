package com.wildsregrown.blocks.decoration;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class GlassWindows extends Block {

    private static final VoxelShape X;
    private static final VoxelShape Z;
    private static final EnumProperty<Direction.Axis> FACING = Properties.HORIZONTAL_AXIS;

    public GlassWindows(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.Axis.X).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING).getPositiveDirection()).getAxis());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING).getPositiveDirection()).getAxis());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, Properties.WATERLOGGED);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        return getDefaultState().with(FACING, direction.getAxis()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
            case X -> {return X;}
            case Z  -> {return Z;}
            default -> {return X;}
        }
    }

    static {
        Z = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.4375, 1, 1, 0.5625)
        );
        X = VoxelTransform.rotate90(Z);

    }

}
