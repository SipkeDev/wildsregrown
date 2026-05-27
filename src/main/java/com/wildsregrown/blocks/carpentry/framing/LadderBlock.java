package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class LadderBlock extends Block implements ITintedBlock {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;

    private final static EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LadderBlock(Properties settings) {
        super(settings.forceSolidOff().sound(SoundType.LADDER).noOcclusion());
        this.registerDefaultState(defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, FACING, WATERLOGGED);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection().getOpposite();
        return defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        switch (direction) {
            case NORTH -> {return NORTH;}
            case SOUTH  -> {return SOUTH;}
            case EAST  -> {return EAST;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    //N/E/S/W
    static {
        NORTH = Shapes.or(
                Shapes.box(0, 0, 0.75, 0.1875, 1, 1),
                Shapes.box(0.8125, 0, 0.75, 1, 1, 1),
                Shapes.box(0.1875, 0.125, 0.8125, 0.8125, 0.25, 0.9375),
                Shapes.box(0.1875, 0.875, 0.8125, 0.8125, 1, 0.9375),
                Shapes.box(0.1875, 0.625, 0.8125, 0.8125, 0.75, 0.9375),
                Shapes.box(0.1875, 0.375, 0.8125, 0.8125, 0.5, 0.9375)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }
}
