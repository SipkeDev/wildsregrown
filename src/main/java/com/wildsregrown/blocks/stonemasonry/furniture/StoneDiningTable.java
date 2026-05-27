package com.wildsregrown.blocks.stonemasonry.furniture;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.abstracts.HorizontalConnectingBlock;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;

public class StoneDiningTable extends HorizontalConnectingBlock {

    private static final VoxelShape[] single;
    private static final VoxelShape[] middle;
    private static final VoxelShape[] left;
    private static final VoxelShape[] right;
    private static final EnumProperty<HorizontalConnected> SHAPE = WRGProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public StoneDiningTable(Properties settings){
        super(settings);
        //this.registerDefaultState(defaultBlockState().setValue(SHAPE, HorizontalConnected.SINGLE).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean isConnectingBlock(BlockState neighbour, BlockState state) {
        return neighbour.getBlock() instanceof StoneDiningTable;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(SHAPE, FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)){
            case NORTH -> {
                return getShape(state, 0);
            }
            case EAST -> {
                return getShape(state, 1);
            }
            case SOUTH -> {
                return getShape(state, 2);
            }
            case WEST -> {
                return getShape(state, 3);
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    private static VoxelShape getShape(BlockState state, int i){
        switch (state.getValue(SHAPE)){
            case SINGLE -> {
                return single[i];
            }
            case MIDDLE -> {
                return middle[i];
            }
            case LEFT -> {
                return left[i];
            }
            case RIGHT -> {
                return right[i];
            }
        }
        return Shapes.block();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    static {
        single = new VoxelShape[4];
        single[0] = Shapes.or(
                Shapes.box(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0.0625, 0.1875, 0.40625, 0.125, 0.3125, 0.59375),
                Shapes.box(0.0625, 0.375, 0.3125, 0.125, 0.4375, 0.6875),
                Shapes.box(0.0625, 0.0625, 0.3125, 0.125, 0.125, 0.6875),
                Shapes.box(0.0625, 0.125, 0.375, 0.125, 0.1875, 0.625),
                Shapes.box(0.0625, 0.3125, 0.375, 0.125, 0.375, 0.625),
                Shapes.box(0.03125, 0, 0.3125, 0.15625, 0.0625, 0.4375),
                Shapes.box(0.03125, 0, 0.5625, 0.15625, 0.0625, 0.6875),
                Shapes.box(0.84375, 0, 0.5625, 0.96875, 0.0625, 0.6875),
                Shapes.box(0.875, 0.1875, 0.40625, 0.9375, 0.3125, 0.59375),
                Shapes.box(0.875, 0.375, 0.3125, 0.9375, 0.4375, 0.6875),
                Shapes.box(0.875, 0.0625, 0.3125, 0.9375, 0.125, 0.6875),
                Shapes.box(0.875, 0.125, 0.375, 0.9375, 0.1875, 0.625),
                Shapes.box(0.875, 0.3125, 0.375, 0.9375, 0.375, 0.625),
                Shapes.box(0.84375, 0, 0.3125, 0.96875, 0.0625, 0.4375),
                Shapes.box(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        single[1] = VoxelTransform.rotate90(single[0]);
        single[2] = VoxelTransform.rotate180(single[0]);
        single[3] = VoxelTransform.rotate270(single[0]);

        middle = new VoxelShape[4];
        middle[0] = Shapes.or(
                Shapes.box(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        middle[1] = VoxelTransform.rotate90(middle[0]);
        middle[2] = VoxelTransform.rotate180(middle[0]);
        middle[3] = VoxelTransform.rotate270(middle[0]);

        left = new VoxelShape[4];
        left[0] = Shapes.or(
                Shapes.box(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0.0625, 0.1875, 0.40625, 0.125, 0.3125, 0.59375),
                Shapes.box(0.0625, 0.375, 0.3125, 0.125, 0.4375, 0.6875),
                Shapes.box(0.0625, 0.0625, 0.3125, 0.125, 0.125, 0.6875),
                Shapes.box(0.0625, 0.125, 0.375, 0.125, 0.1875, 0.625),
                Shapes.box(0.0625, 0.3125, 0.375, 0.125, 0.375, 0.625),
                Shapes.box(0.03125, 0, 0.3125, 0.15625, 0.0625, 0.4375),
                Shapes.box(0.03125, 0, 0.5625, 0.15625, 0.0625, 0.6875),
                Shapes.box(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        left[1] = VoxelTransform.rotate90(left[0]);
        left[2] = VoxelTransform.rotate180(left[0]);
        left[3] = VoxelTransform.rotate270(left[0]);

        right = new VoxelShape[4];
        right[0] = VoxelTransform.mirrorX(left[0]);
        right[1] = VoxelTransform.rotate90(right[0]);
        right[2] = VoxelTransform.rotate180(right[0]);
        right[3] = VoxelTransform.rotate270(right[0]);
    }

}
