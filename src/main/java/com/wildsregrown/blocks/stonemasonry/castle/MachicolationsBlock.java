package com.wildsregrown.blocks.stonemasonry.castle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.abstracts.VerticalConnectingFacingBlock;
import wildsregrown.api.block.properties.WRGProperties;

public class MachicolationsBlock extends VerticalConnectingFacingBlock {

    private static final VoxelShape[] SINGLE;
    private static final VoxelShape[] TOP;
    private static final VoxelShape[] MIDDLE;
    private static final VoxelShape[] BOTTOM;

    public MachicolationsBlock(Properties settings) {
        super(settings);
        //this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(WRGProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState blockState) {
        return blockState.getBlock() instanceof MachicolationsBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int i;
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST  -> i = 1;
            case NORTH -> i = 2;
            case WEST  -> i = 3;
            default    -> i = 0;
        }

        switch (state.getValue(WRGProperties.VERTICAL_CONNECTED)) {
            case TOP    -> {return TOP[i];}
            case MIDDLE -> {return MIDDLE[i];}
            case BOTTOM -> {return BOTTOM[i];}
            default     -> {return SINGLE[i];}
        }
    }
    static {
        VoxelShape shape = Shapes.or(
                Block.box(0, 10, 0,4, 16, 16),
                Block.box(12, 10, 0,16, 16, 16),
                Block.box(12, 4, 4,16, 10, 16),
                Block.box(0, 4, 4,4, 10, 16),
                Block.box(12, 0, 8,16, 4, 16),
                Block.box(0, 0, 8,4, 4, 16));
        SINGLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = Shapes.or(
                Block.box(0, 7, 0,4, 16, 16),
                Block.box(12, 7, 0,16, 16, 16),
                Block.box(12, 0, 3,16, 7, 16),
                Block.box(0, 0, 3,4, 7, 16));
        TOP = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = Shapes.or(
                Block.box(0, 0, 8,4, 16, 16),
                Block.box(12, 0, 8,16, 16, 16));
        MIDDLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = Shapes.or(
                Block.box(0, 7, 8, 4, 16, 16),
                Block.box(12, 7, 8, 16, 16, 16),
                Block.box(12, 0, 13, 16, 7, 16),
                Block.box(0, 0, 13, 4, 7, 16));
        BOTTOM = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };
    }
}
