package com.wildsregrown.blocks.stonemasonry.castle;

import com.wildsregrown.blocks.properties.OrdinalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class HalfArchBlock extends StairBlock {

    private static final VoxelShape[] INNER;
    private static final VoxelShape[] OUTER;
    private static final VoxelShape[] STRAIGHT;

    public HalfArchBlock(BlockState state, Properties settings) {
        super(state, settings);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (neighborState.getBlock() instanceof StairBlock) {
            return super.updateShape(state, world, tickView, pos, direction,  neighborPos, neighborState, random);
        }
        return state;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        OrdinalDirection d = OrdinalDirection.getOrdinal(state.getValue(FACING));
        StairsShape shape = state.getValue(BlockStateProperties.STAIRS_SHAPE);
        Half half = state.getValue(HALF);
        switch (shape) {
            default -> {return STRAIGHT[half == Half.TOP ? d.ordinal()/2 : d.ordinal()/2 + 4];}
            case INNER_LEFT -> {return INNER[half == Half.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case INNER_RIGHT-> {return INNER[half == Half.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
            case OUTER_LEFT -> {return OUTER[half == Half.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case OUTER_RIGHT-> {return OUTER[half == Half.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
        }
    }
    static {
        VoxelShape shape = Shapes.or(
                Block.box(0, 12, 0 , 16, 16, 16),
                Block.box(0, 8 , 9.5 , 16, 16, 16),
                Block.box(0, 0 , 12, 16, 16, 16));
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
                Shapes.or(STRAIGHT[0], STRAIGHT[1]),//S
                Shapes.or(STRAIGHT[1], STRAIGHT[2]),//W
                Shapes.or(STRAIGHT[2], STRAIGHT[3]),//N
                Shapes.or(STRAIGHT[3], STRAIGHT[0]),//E
                Shapes.or(STRAIGHT[4], STRAIGHT[5]),//S_
                Shapes.or(STRAIGHT[5], STRAIGHT[6]),//W_
                Shapes.or(STRAIGHT[6], STRAIGHT[7]),//N_
                Shapes.or(STRAIGHT[7], STRAIGHT[4]) //E_
        };

        shape_ = Shapes.or(
                Block.box(0, 0, 0 , 16, 4, 16),
                Block.box(8, 4 , 8 , 16, 6, 16),
                Block.box(10, 6, 10, 16, 8, 16),
                Block.box(12, 8 , 12, 16, 16, 16));
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
