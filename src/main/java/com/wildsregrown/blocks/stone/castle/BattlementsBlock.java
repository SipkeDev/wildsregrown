package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BattlementsBlock extends Block {

    private static final VoxelShape SOUTH;
    private static final VoxelShape NORTH;
    private static final VoxelShape WEST;
    private static final VoxelShape EAST;

    public BattlementsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING, Properties.WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        return getDefaultState().with(Properties.HORIZONTAL_FACING, direction.getOpposite()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
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
        NORTH = VoxelTransform.rotate180(VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.0625, 0.125, 1, 0.125, 0.5),
                VoxelShapes.cuboid(0, 0, 0.0625, 1, 0.0625, 0.5625),
                VoxelShapes.cuboid(0, 0.125, 0.1875, 1, 0.1875, 0.4375),
                VoxelShapes.cuboid(0, 0.1875, 0.25, 1, 0.25, 0.375)
                )
        );
        EAST = VoxelTransform.rotate90(NORTH);
        SOUTH = VoxelTransform.rotate180(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }

}
