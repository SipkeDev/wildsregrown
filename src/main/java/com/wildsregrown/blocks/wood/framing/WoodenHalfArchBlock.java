package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.stone.castle.HalfArchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WoodenHalfArchBlock extends HalfArchBlock implements ITintedBlock {

    private static final VoxelShape[] INNER;
    private static final VoxelShape[] OUTER;
    private static final VoxelShape[] STRAIGHT;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public WoodenHalfArchBlock(BlockState state, Settings settings) {
        super(state, settings);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);

        BlockState blockState = getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing())
                .with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > (double)0.5F)) ? BlockHalf.BOTTOM : BlockHalf.TOP)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

        return blockState.with(SHAPE, getStairShape(blockState, ctx.getWorld(), blockPos));
    }

    public static StairShape getStairShape(BlockState state, BlockView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockState blockState = world.getBlockState(pos.offset(direction));

        if (isStairs(blockState) && state.get(HALF) == blockState.get(HALF)) {
            Direction dir2 = blockState.get(FACING);
            if (dir2.getAxis() != (state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, dir2.getOpposite())) {
                if (dir2 == direction.rotateYCounterclockwise()) {
                    return StairShape.OUTER_LEFT;
                }

                return StairShape.OUTER_RIGHT;
            }
        }

        BlockState dir2 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (isStairs(dir2) && state.get(HALF) == dir2.get(HALF)) {
            Direction dir3 = dir2.get(FACING);
            if (dir3.getAxis() != (state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, dir3)) {
                if (dir3 == direction.rotateYCounterclockwise()) {
                    return StairShape.INNER_LEFT;
                }

                return StairShape.INNER_RIGHT;
            }
        }

        return StairShape.STRAIGHT;
    }

    private static boolean isDifferentOrientation(BlockState state, BlockView world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlockState(pos.offset(dir));
        return !isStairs(blockState) || blockState.get(FACING) != state.get(FACING) || blockState.get(HALF) != state.get(HALF);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, HALF, SHAPE, WATERLOGGED);
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
