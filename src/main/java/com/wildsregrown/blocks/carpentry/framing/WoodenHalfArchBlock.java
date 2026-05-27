package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import com.wildsregrown.blocks.stonemasonry.castle.HalfArchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenHalfArchBlock extends HalfArchBlock implements ITintedBlock {

    private static final VoxelShape[] INNER;
    private static final VoxelShape[] OUTER;
    private static final VoxelShape[] STRAIGHT;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public WoodenHalfArchBlock(BlockState state, Properties settings) {
        super(state, settings);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        Direction direction = ctx.getClickedFace();
        BlockPos blockPos = ctx.getClickedPos();
        FluidState fluidState = ctx.getLevel().getFluidState(blockPos);

        BlockState blockState = defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection())
                .setValue(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - (double)blockPos.getY() > (double)0.5F)) ? Half.BOTTOM : Half.TOP)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);

        return blockState.setValue(SHAPE, getStairsShape(blockState, ctx.getLevel(), blockPos));
    }

    public static StairsShape getStairsShape(BlockState state, BlockGetter world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockState blockState = world.getBlockState(pos.relative(direction));

        if (isStairs(blockState) && state.getValue(HALF) == blockState.getValue(HALF)) {
            Direction dir2 = blockState.getValue(FACING);
            if (dir2.getAxis() != (state.getValue(FACING)).getAxis() && canTakeShape(state, world, pos, dir2.getOpposite())) {
                if (dir2 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState dir2 = world.getBlockState(pos.relative(direction.getOpposite()));
        if (isStairs(dir2) && state.getValue(HALF) == dir2.getValue(HALF)) {
            Direction dir3 = dir2.getValue(FACING);
            if (dir3.getAxis() != (state.getValue(FACING)).getAxis() && canTakeShape(state, world, pos, dir3)) {
                if (dir3 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlockState(pos.relative(dir));
        return !isStairs(blockState) || blockState.getValue(FACING) != state.getValue(FACING) || blockState.getValue(HALF) != state.getValue(HALF);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        OrdinalDirection d = OrdinalDirection.getOrdinal(state.getValue(FACING));
        StairsShape shape = state.getValue(BlockStateProperties.STAIRS_SHAPE);
        Half half = state.getValue(HALF);
        switch (shape) {
            case INNER_LEFT -> {return INNER[half == Half.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case INNER_RIGHT-> {return INNER[half == Half.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
            case OUTER_LEFT -> {return OUTER[half == Half.TOP ? (d.cClockwise().ordinal()-1)/2 : (d.cClockwise().ordinal()-1)/2 + 4];}
            case OUTER_RIGHT-> {return OUTER[half == Half.TOP ? (d.clockwise().ordinal() -1)/2 : (d.clockwise().ordinal() -1)/2 + 4];}
            default -> {return STRAIGHT[half == Half.TOP ? d.ordinal()/2 : d.ordinal()/2 + 4];}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
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
