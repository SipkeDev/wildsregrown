package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.CornerConnecting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class Balustrade extends Block implements Waterloggable {

    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] WEST;

    public Balustrade(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ModProperties.CORNER_CONNECTING, CornerConnecting.NONE).with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ModProperties.CORNER_CONNECTING, Properties.WATERLOGGED, Properties.HORIZONTAL_FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return  state.with(Properties.HORIZONTAL_FACING, rotation.rotate((state.get(Properties.HORIZONTAL_FACING))));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        if (mirror == BlockMirror.NONE) {return state;}
        if (state.get(ModProperties.CORNER_CONNECTING) == CornerConnecting.NONE) {
            Direction.Axis a = state.get(Properties.HORIZONTAL_FACING).getAxis();
            if ((a == Direction.Axis.Z && mirror == BlockMirror.LEFT_RIGHT)
             || (a == Direction.Axis.X && mirror == BlockMirror.FRONT_BACK)) {
                return state.with(Properties.HORIZONTAL_FACING, state.get(Properties.HORIZONTAL_FACING).getOpposite());
            }
            return state;
        }
        if (mirror == BlockMirror.FRONT_BACK) {
            return rotate(state, BlockRotation.CLOCKWISE_90);
        }
        return rotate(state, BlockRotation.COUNTERCLOCKWISE_90);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }
    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
        double p5 = 0.5;
        double x = p5 + ctx.getBlockPos().getX() - ctx.getHitPos().x;
        double y = p5 + ctx.getBlockPos().getY() - ctx.getHitPos().y;
        double z = p5 + ctx.getBlockPos().getZ() - ctx.getHitPos().z;
        double i = 0.25;
        Direction side = ctx.getSide();

        return ctx.getStack().isOf(this.asItem())
                && !(state.get(ModProperties.CORNER_CONNECTING) == CornerConnecting.RIGHT_NOOK)
                && !(ctx.getPlayer().isSneaking()
                    || (y ==  p5 && side == Direction.DOWN)
                    || (y == -p5 && side == Direction.UP)
                    || (x >= -p5 && x <-i && side == Direction.EAST)
                    || (x <=  p5 && x > i && side == Direction.WEST)
                    || (z >= -p5 && z <-i && side == Direction.SOUTH)
                    || (z <=  p5 && z > i && side == Direction.NORTH));
    }



    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        boolean waterlogged = ctx.getWorld().getFluidState(pos).getFluid() == Fluids.WATER;
        BlockState state = ctx.getWorld().getBlockState(pos);
        Direction d1 = ctx.getSide();
        double x = 0.5 + pos.getX() - ctx.getHitPos().x;
        double z = 0.5 + pos.getZ() - ctx.getHitPos().z;
        boolean N = z >= 0;
        boolean W = x >= 0;

        if (!state.isOf(this.asBlock())) {
            return getDefaultState().with(Properties.HORIZONTAL_FACING, newQuadrant(N,W)).with(ModProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_CORNER).with(Properties.WATERLOGGED, waterlogged);
        }
        state = state.with(Properties.WATERLOGGED, waterlogged);

        boolean inBlock = x < 0.5 && x > -0.5
                       && z < 0.5 && z > -0.5;
        if (inBlock) {
            switch (d1) {
                default -> {}
                case NORTH -> N = true;
                case SOUTH -> N = false;
                case EAST  -> W = false;
                case WEST  -> W = true;
            }
        }

        Direction d2 = newQuadrant(N,W);
        d1 = state.get(Properties.HORIZONTAL_FACING);
        if (state.get(ModProperties.CORNER_CONNECTING) == CornerConnecting.RIGHT_CORNER) {

            if (d2 == d1.rotateYCounterclockwise()) {
                return state.with(ModProperties.CORNER_CONNECTING, CornerConnecting.NONE);
            }
            if (d2 == d1.rotateYClockwise()) {
                return state.with(ModProperties.CORNER_CONNECTING, CornerConnecting.NONE).with(Properties.HORIZONTAL_FACING, d2);
            } else {
                return null;
            }
        }
        if (d2 == d1.getOpposite()) {
            return state.with(ModProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_NOOK).with(Properties.HORIZONTAL_FACING, d2.rotateYClockwise());
        }
        if (d2 == d1.rotateYClockwise()) {
            return state.with(ModProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_NOOK);
        }
        return null;
    }

    private static Direction newQuadrant(boolean N, boolean W) {
        if (W && N) {return Direction.WEST;}
        if (!(W || N)) {return Direction.EAST;}
        if (W) {return Direction.SOUTH;}
        return Direction.NORTH;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        int i = state.get(ModProperties.CORNER_CONNECTING).ordinal();

        switch (state.get(Properties.HORIZONTAL_FACING)) {
            default   -> {return NORTH[i];}
            case EAST -> {return EAST[i];}
            case SOUTH-> {return SOUTH[i];}
            case WEST -> {return WEST[i];}
        }
    }

    static {
        SOUTH = new VoxelShape[]{
                VoxelShapes.union(
                        Block.createCuboidShape(2.6, 4, 10.6,5.4, 13, 13.4),
                        Block.createCuboidShape(10.6, 4, 10.6,13.4, 13, 13.4),
                        Block.createCuboidShape(0, 13, 9,16, 16, 15),
                        Block.createCuboidShape(0, 0, 9,16, 2, 15),
                        Block.createCuboidShape(2, 2, 10,6, 4, 14),
                        Block.createCuboidShape(10, 2, 10,14, 4, 14)),
                VoxelShapes.union(
                        Block.createCuboidShape(0, 0, 8,8, 2, 16),
                        Block.createCuboidShape(1, 2, 9,7, 13, 15),
                        Block.createCuboidShape(0, 13, 8,8, 16, 16)),
                VoxelShapes.union(
                        Block.createCuboidShape(8, 13, 9,16, 16, 15),
                        Block.createCuboidShape(8, 0, 9,16, 2, 15),
                        Block.createCuboidShape(10, 2, 10,14, 4, 14),
                        Block.createCuboidShape(10.6, 4, 10.6,13.4, 13, 13.4),
                        Block.createCuboidShape(2.6, 4, 2.6,5.4, 13, 5.4),
                        Block.createCuboidShape(2, 2, 2,6, 4, 6),
                        Block.createCuboidShape(1, 0, 0,7, 2, 8),
                        Block.createCuboidShape(1, 13, 0,7, 16, 8),
                        Block.createCuboidShape(0, 0, 8,8, 2, 16),
                        Block.createCuboidShape(1, 2, 9,7, 13, 15),
                        Block.createCuboidShape(0, 13, 8,8, 16, 16))
        };
        EAST = new VoxelShape[]{
                VoxelTransform.rotate270(SOUTH[0]),
                VoxelTransform.rotate270(SOUTH[1]),
                VoxelTransform.rotate270(SOUTH[2])
        };
        NORTH = new VoxelShape[]{
                VoxelTransform.rotate180(SOUTH[0]),
                VoxelTransform.rotate180(SOUTH[1]),
                VoxelTransform.rotate180(SOUTH[2])
        };
        WEST = new VoxelShape[]{
                VoxelTransform.rotate90(SOUTH[0]),
                VoxelTransform.rotate90(SOUTH[1]),
                VoxelTransform.rotate90(SOUTH[2])
        };
    }
}