package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.VerticalConnectingFacingBlock;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MachicolationsBlock extends VerticalConnectingFacingBlock {
    private static final VoxelShape[] SINGLE;
    private static final VoxelShape[] TOP;
    private static final VoxelShape[] MIDDLE;
    private static final VoxelShape[] BOTTOM;

    public MachicolationsBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(ModProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState blockState) {
        return blockState.getBlock() instanceof MachicolationsBlock;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i;
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            default    -> i = 0;
            case EAST  -> i = 1;
            case NORTH -> i = 2;
            case WEST  -> i = 3;
        }

        switch (state.get(ModProperties.VERTICAL_CONNECTED)) {
            default     -> {return SINGLE[i];}
            case TOP    -> {return TOP[i];}
            case MIDDLE -> {return MIDDLE[i];}
            case BOTTOM -> {return BOTTOM[i];}
        }
    }
    static {
        VoxelShape shape = VoxelShapes.union(
                Block.createCuboidShape(0, 10, 0,4, 16, 16),
                Block.createCuboidShape(12, 10, 0,16, 16, 16),
                Block.createCuboidShape(12, 4, 4,16, 10, 16),
                Block.createCuboidShape(0, 4, 4,4, 10, 16),
                Block.createCuboidShape(12, 0, 8,16, 4, 16),
                Block.createCuboidShape(0, 0, 8,4, 4, 16));
        SINGLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = VoxelShapes.union(
                Block.createCuboidShape(0, 7, 0,4, 16, 16),
                Block.createCuboidShape(12, 7, 0,16, 16, 16),
                Block.createCuboidShape(12, 0, 3,16, 7, 16),
                Block.createCuboidShape(0, 0, 3,4, 7, 16));
        TOP = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 8,4, 16, 16),
                Block.createCuboidShape(12, 0, 8,16, 16, 16));
        MIDDLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };

        shape = VoxelShapes.union(
                Block.createCuboidShape(0, 7, 8, 4, 16, 16),
                Block.createCuboidShape(12, 7, 8, 16, 16, 16),
                Block.createCuboidShape(12, 0, 13, 16, 7, 16),
                Block.createCuboidShape(0, 0, 13, 4, 7, 16));
        BOTTOM = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape)
        };
    }
}
