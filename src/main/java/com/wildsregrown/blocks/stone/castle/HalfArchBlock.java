package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class HalfArchBlock extends StairsBlock {

    private static final VoxelShape[] INNER;
    private static final VoxelShape[] OUTER;
    private static final VoxelShape[] STRAIGHT;

    public HalfArchBlock(BlockState state, Settings settings) {
        super(state, settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (neighborState.getBlock() instanceof StairsBlock) {
            return super.getStateForNeighborUpdate(state, world, tickView, pos, direction,  neighborPos, neighborState, random);
        }
        return state;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        OrdinalDirection d = OrdinalDirection.getOrdinal(state.get(FACING));
        StairShape shape = state.get(Properties.STAIR_SHAPE);
        BlockHalf half = state.get(HALF);
        switch (shape) {
            default -> {return STRAIGHT[half == BlockHalf.TOP ? d.ordinal()/2 : d.ordinal()/2 + 4];}
            case INNER_LEFT -> {return INNER[half == BlockHalf.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case INNER_RIGHT-> {return INNER[half == BlockHalf.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
            case OUTER_LEFT -> {return OUTER[half == BlockHalf.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case OUTER_RIGHT-> {return OUTER[half == BlockHalf.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
        }
    }
    static {
        VoxelShape shape = VoxelShapes.union(
                Block.createCuboidShape(0, 12, 0 , 16, 16, 16),
                Block.createCuboidShape(0, 8 , 9.5 , 16, 16, 16),
                Block.createCuboidShape(0, 0 , 12, 16, 16, 16));
        VoxelShape shape_ = VoxelTransform.mirrorY(shape);

        STRAIGHT = new VoxelShape[]{
                VoxelTransform.rotate180(shape),//S
                VoxelTransform.rotate270(shape),//W
                shape,                          //N
                VoxelTransform.rotate90(shape), //E
                VoxelTransform.rotate180(shape_),//S_
                VoxelTransform.rotate270(shape_),//W_
                shape_,                          //N_
                VoxelTransform.rotate90(shape_), //E_
        };
        INNER = new VoxelShape[]{
                VoxelShapes.union(STRAIGHT[0], STRAIGHT[1]),//S
                VoxelShapes.union(STRAIGHT[1], STRAIGHT[2]),//W
                VoxelShapes.union(STRAIGHT[2], STRAIGHT[3]),//N
                VoxelShapes.union(STRAIGHT[3], STRAIGHT[0]),//E
                VoxelShapes.union(STRAIGHT[4], STRAIGHT[5]),//S_
                VoxelShapes.union(STRAIGHT[5], STRAIGHT[6]),//W_
                VoxelShapes.union(STRAIGHT[6], STRAIGHT[7]),//N_
                VoxelShapes.union(STRAIGHT[7], STRAIGHT[4]) //E_
        };

        shape_ = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0 , 16, 4, 16),
                Block.createCuboidShape(8, 4 , 8 , 16, 6, 16),
                Block.createCuboidShape(10, 6, 10, 16, 8, 16),
                Block.createCuboidShape(12, 8 , 12, 16, 16, 16));
        shape = VoxelTransform.mirrorY(shape_);

        OUTER = new VoxelShape[]{
                VoxelTransform.mirrorZ(shape), //E
                shape,                          //N
                VoxelTransform.mirrorX(shape),//W
                VoxelTransform.rotate180(shape),//S
                VoxelTransform.mirrorZ(shape_), //E_
                shape_,                          //N_
                VoxelTransform.mirrorX(shape_),//W_
                VoxelTransform.rotate180(shape_),//S_
        };
    }
}
