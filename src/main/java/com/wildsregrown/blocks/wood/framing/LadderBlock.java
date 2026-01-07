package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LadderBlock extends Block implements ITintedBlock {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;

    private final static EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public LadderBlock(Settings settings) {
        super(settings.notSolid().sounds(BlockSoundGroup.LADDER).nonOpaque());
        this.setDefaultState(getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, FACING, WATERLOGGED);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        switch (direction) {
            case NORTH -> {return NORTH;}
            case SOUTH  -> {return SOUTH;}
            case EAST  -> {return EAST;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
    }

    //N/E/S/W
    static {
        NORTH = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.75, 0.1875, 1, 1),
                VoxelShapes.cuboid(0.8125, 0, 0.75, 1, 1, 1),
                VoxelShapes.cuboid(0.1875, 0.125, 0.8125, 0.8125, 0.25, 0.9375),
                VoxelShapes.cuboid(0.1875, 0.875, 0.8125, 0.8125, 1, 0.9375),
                VoxelShapes.cuboid(0.1875, 0.625, 0.8125, 0.8125, 0.75, 0.9375),
                VoxelShapes.cuboid(0.1875, 0.375, 0.8125, 0.8125, 0.5, 0.9375)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }
}
