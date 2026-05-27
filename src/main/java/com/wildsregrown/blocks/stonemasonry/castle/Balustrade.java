package com.wildsregrown.blocks.stonemasonry.castle;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.CornerConnecting;

public class Balustrade extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] WEST;

    public Balustrade(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.NONE).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WRGProperties.CORNER_CONNECTING, BlockStateProperties.WATERLOGGED, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return  state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate((state.getValue(BlockStateProperties.HORIZONTAL_FACING))));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE) {return state;}
        if (state.getValue(WRGProperties.CORNER_CONNECTING) == CornerConnecting.NONE) {
            Direction.Axis a = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis();
            if ((a == Direction.Axis.Z && mirror == Mirror.LEFT_RIGHT)
             || (a == Direction.Axis.X && mirror == Mirror.FRONT_BACK)) {
                return state.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
            }
            return state;
        }
        if (mirror == Mirror.FRONT_BACK) {
            return rotate(state, Rotation.CLOCKWISE_90);
        }
        return rotate(state, Rotation.COUNTERCLOCKWISE_90);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return state;
    }
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        double p5 = 0.5;
        double x = p5 + ctx.getClickedPos().getX() - ctx.getClickLocation().x;
        double y = p5 + ctx.getClickedPos().getY() - ctx.getClickLocation().y;
        double z = p5 + ctx.getClickedPos().getZ() - ctx.getClickLocation().z;
        double i = 0.25;
        Direction side = ctx.getClickedFace();

        return ctx.getItemInHand().is(this.asItem())
                && !(state.getValue(WRGProperties.CORNER_CONNECTING) == CornerConnecting.RIGHT_NOOK)
                && !(ctx.getPlayer().isShiftKeyDown()
                    || (y ==  p5 && side == Direction.DOWN)
                    || (y == -p5 && side == Direction.UP)
                    || (x >= -p5 && x <-i && side == Direction.EAST)
                    || (x <=  p5 && x > i && side == Direction.WEST)
                    || (z >= -p5 && z <-i && side == Direction.SOUTH)
                    || (z <=  p5 && z > i && side == Direction.NORTH));
    }



    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        boolean waterlogged = ctx.getLevel().getFluidState(pos).getType() == Fluids.WATER;
        BlockState state = ctx.getLevel().getBlockState(pos);
        Direction d1 = ctx.getClickedFace();
        double x = 0.5 + pos.getX() - ctx.getClickLocation().x;
        double z = 0.5 + pos.getZ() - ctx.getClickLocation().z;
        boolean N = z >= 0;
        boolean W = x >= 0;

        if (!state.is(this.asBlock())) {
            return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, newQuadrant(N,W)).setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_CORNER).setValue(BlockStateProperties.WATERLOGGED, waterlogged);
        }
        state = state.setValue(BlockStateProperties.WATERLOGGED, waterlogged);

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
        d1 = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (state.getValue(WRGProperties.CORNER_CONNECTING) == CornerConnecting.RIGHT_CORNER) {

            if (d2 == d1.getCounterClockWise()) {
                return state.setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.NONE);
            }
            if (d2 == d1.getClockWise()) {
                return state.setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.NONE).setValue(BlockStateProperties.HORIZONTAL_FACING, d2);
            } else {
                return null;
            }
        }
        if (d2 == d1.getOpposite()) {
            return state.setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_NOOK).setValue(BlockStateProperties.HORIZONTAL_FACING, d2.getClockWise());
        }
        if (d2 == d1.getClockWise()) {
            return state.setValue(WRGProperties.CORNER_CONNECTING, CornerConnecting.RIGHT_NOOK);
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
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int i = state.getValue(WRGProperties.CORNER_CONNECTING).ordinal();

        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            default   -> {return NORTH[i];}
            case EAST -> {return EAST[i];}
            case SOUTH-> {return SOUTH[i];}
            case WEST -> {return WEST[i];}
        }
    }

    static {
        SOUTH = new VoxelShape[]{
                Shapes.or(
                        Block.box(2.6, 4, 10.6,5.4, 13, 13.4),
                        Block.box(10.6, 4, 10.6,13.4, 13, 13.4),
                        Block.box(0, 13, 9,16, 16, 15),
                        Block.box(0, 0, 9,16, 2, 15),
                        Block.box(2, 2, 10,6, 4, 14),
                        Block.box(10, 2, 10,14, 4, 14)),
                Shapes.or(
                        Block.box(0, 0, 8,8, 2, 16),
                        Block.box(1, 2, 9,7, 13, 15),
                        Block.box(0, 13, 8,8, 16, 16)),
                Shapes.or(
                        Block.box(8, 13, 9,16, 16, 15),
                        Block.box(8, 0, 9,16, 2, 15),
                        Block.box(10, 2, 10,14, 4, 14),
                        Block.box(10.6, 4, 10.6,13.4, 13, 13.4),
                        Block.box(2.6, 4, 2.6,5.4, 13, 5.4),
                        Block.box(2, 2, 2,6, 4, 6),
                        Block.box(1, 0, 0,7, 2, 8),
                        Block.box(1, 13, 0,7, 16, 8),
                        Block.box(0, 0, 8,8, 2, 16),
                        Block.box(1, 2, 9,7, 13, 15),
                        Block.box(0, 13, 8,8, 16, 16))
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