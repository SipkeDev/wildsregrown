package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ThinPillar extends Pillar {
    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] WEST;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] SINGLE;

    //Defining Default BlockState
    public ThinPillar(Settings settings) {
        super(settings);
    }
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction direction = state.get(Properties.HOPPER_FACING);
        int i = state.get(ModProperties.VARIATIONS_2) == 1 ? 0 : 4;

        switch (state.get(ModProperties.VERTICAL_CONNECTED)) {
            case BOTTOM -> i += 1;
            case MIDDLE -> i += 2;
            case TOP    -> i += 3;
            default     -> {}
        }

        switch (direction) {
            case NORTH -> {return NORTH[i];}
            case EAST  -> {return EAST[i];}
            case SOUTH -> {return SOUTH[i];}
            case WEST  -> {return WEST[i];}
            default    -> {return SINGLE[i];}
        }
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {
        return currentState.getBlock() instanceof ThinPillar && validState.getBlock() instanceof ThinPillar && currentState.get(Properties.HOPPER_FACING) == validState.get(Properties.HOPPER_FACING);
    }


    static {

        SINGLE = new VoxelShape[]{
                VoxelShapes.union(
                        Block.createCuboidShape(4, 12, 4, 12, 16, 12),
                        Block.createCuboidShape(5, 4, 5, 11, 12, 11),
                        Block.createCuboidShape(4, 0, 4, 12, 4, 12)),
                VoxelShapes.union(
                        Block.createCuboidShape(5, 4, 5, 11, 8, 11),
                        Block.createCuboidShape(6, 8, 6, 10, 16, 10),
                        Block.createCuboidShape(4, 0, 4, 12, 4, 12),
                        Block.createCuboidShape(6, 0, 6, 10, 16, 10)),
                Block.createCuboidShape(6, 0, 6, 10, 16, 10),
                VoxelShapes.union(
                        Block.createCuboidShape(5, 8, 5, 11, 12, 11),
                        Block.createCuboidShape(6, 0, 6, 10, 8, 10),
                        Block.createCuboidShape(4, 12, 4, 12, 16, 12)),
                VoxelShapes.union(
                        Block.createCuboidShape(5, 4, 5, 7, 14, 7),
                        Block.createCuboidShape(5, 4, 9, 7, 14, 11),
                        Block.createCuboidShape(9, 4, 5, 11, 14, 7),
                        Block.createCuboidShape(9, 4, 9, 11, 14, 11),
                        Block.createCuboidShape(6, 4, 6, 10, 14, 10),
                        Block.createCuboidShape(4, 14, 4, 12, 16, 12),
                        Block.createCuboidShape(4, 0, 4, 12, 2, 12),
                        Block.createCuboidShape(5, 2, 5, 11, 4, 11)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 4, 12, 4, 12),
                        Block.createCuboidShape(4, 4, 4, 6, 16, 6),
                        Block.createCuboidShape(4, 4, 10, 6, 16, 12),
                        Block.createCuboidShape(10, 4, 4, 12, 16, 6),
                        Block.createCuboidShape(10, 4, 10, 12, 16, 12),
                        Block.createCuboidShape(5, 4, 5, 11, 16, 11)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 4, 6, 16, 6),
                        Block.createCuboidShape(4, 0, 10, 6, 16, 12),
                        Block.createCuboidShape(10, 0, 4, 12, 16, 6),
                        Block.createCuboidShape(10, 0, 10, 12, 16, 12),
                        Block.createCuboidShape(5, 0, 5, 11, 16, 11)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 4, 12, 4, 12),
                        Block.createCuboidShape(10, 4, 4, 12, 14, 6),
                        Block.createCuboidShape(4, 4, 4, 6, 14, 6),
                        Block.createCuboidShape(4, 4, 10, 6, 14, 12),
                        Block.createCuboidShape(10, 4, 10, 12, 14, 12),
                        Block.createCuboidShape(5, 4, 5, 11, 14, 11),
                        Block.createCuboidShape(4, 14, 4, 12, 16, 12)),

        };
        SOUTH = new VoxelShape[]{
                VoxelShapes.union(
                        Block.createCuboidShape(5, 4, 13, 11, 12, 16),
                        Block.createCuboidShape(4, 12, 12, 12, 16, 16),
                        Block.createCuboidShape(4, 0, 12, 12, 4, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(5, 4, 13, 11, 8, 16),
                        Block.createCuboidShape(6, 8, 14, 10, 16, 16),
                        Block.createCuboidShape(4, 0, 12, 12, 4, 16)),
                Block.createCuboidShape(6, 0, 14, 10, 16, 16),
                VoxelShapes.union(
                        Block.createCuboidShape(6, 0, 14, 10, 8, 16),
                        Block.createCuboidShape(5, 8, 13, 11, 12, 16),
                        Block.createCuboidShape(4, 12, 12, 12, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(5, 4, 12, 7, 14, 14),
                        Block.createCuboidShape(9, 4, 12, 11, 14, 14),
                        Block.createCuboidShape(6, 4, 13, 10, 14, 16),
                        Block.createCuboidShape(4, 14, 11, 12, 16, 16),
                        Block.createCuboidShape(4, 0, 11, 12, 2, 16),
                        Block.createCuboidShape(5, 2, 12, 11, 4, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 12, 12, 4, 16),
                        Block.createCuboidShape(4, 4, 12, 6, 16, 14),
                        Block.createCuboidShape(10, 4, 12, 12, 16, 14),
                        Block.createCuboidShape(5, 4, 13, 11, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 12, 6, 16, 14),
                        Block.createCuboidShape(10, 0, 12, 12, 16, 14),
                        Block.createCuboidShape(5, 0, 13, 11, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(4, 0, 12, 12, 4, 16),
                        Block.createCuboidShape(10, 4, 12, 12, 14, 14),
                        Block.createCuboidShape(4, 4, 12, 6, 14, 14),
                        Block.createCuboidShape(5, 4, 13, 11, 14, 16),
                        Block.createCuboidShape(4, 14, 12, 12, 16, 16))
        };
        EAST = new VoxelShape[] {
                VoxelTransform.rotate270(SOUTH[0]),
                VoxelTransform.rotate270(SOUTH[1]),
                VoxelTransform.rotate270(SOUTH[2]),
                VoxelTransform.rotate270(SOUTH[3]),
                VoxelTransform.rotate270(SOUTH[4]),
                VoxelTransform.rotate270(SOUTH[5]),
                VoxelTransform.rotate270(SOUTH[6]),
                VoxelTransform.rotate270(SOUTH[7])
        };
        NORTH = new VoxelShape[] {
                VoxelTransform.rotate180(SOUTH[0]),
                VoxelTransform.rotate180(SOUTH[1]),
                VoxelTransform.rotate180(SOUTH[2]),
                VoxelTransform.rotate180(SOUTH[3]),
                VoxelTransform.rotate180(SOUTH[4]),
                VoxelTransform.rotate180(SOUTH[5]),
                VoxelTransform.rotate180(SOUTH[6]),
                VoxelTransform.rotate180(SOUTH[7])
        };
        WEST = new VoxelShape[] {
                VoxelTransform.rotate90(SOUTH[0]),
                VoxelTransform.rotate90(SOUTH[1]),
                VoxelTransform.rotate90(SOUTH[2]),
                VoxelTransform.rotate90(SOUTH[3]),
                VoxelTransform.rotate90(SOUTH[4]),
                VoxelTransform.rotate90(SOUTH[5]),
                VoxelTransform.rotate90(SOUTH[6]),
                VoxelTransform.rotate90(SOUTH[7])
        };
    }
}