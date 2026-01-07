package com.wildsregrown.blocks.stone;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.Quadrant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class Edge extends Block implements Waterloggable {
    private static final VoxelShape[] X;
    private static final VoxelShape[] Y;
    private static final VoxelShape[] Z;
    private static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    private static final EnumProperty<Quadrant> QUADRANT = Quadrant.QUADRANT;
    public Edge(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.Y).with(QUADRANT, Quadrant.PP).with(Properties.WATERLOGGED, false));
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        if (mirror == BlockMirror.NONE) {return state;}
        Quadrant q = state.get(QUADRANT);

        switch (state.get(AXIS)) {
            case X -> {return mirror == BlockMirror.LEFT_RIGHT ? state.with(QUADRANT, q.mirrorH()) : state;}
            case Z -> {return mirror == BlockMirror.FRONT_BACK ? state.with(QUADRANT, q.mirrorH()) : state;}
            default-> {return switch (q) {
                case NP, PN -> state.with(QUADRANT, mirror == BlockMirror.LEFT_RIGHT ? q.rotate() : q.rotateCC());
                case NN, PP -> state.with(QUADRANT, mirror == BlockMirror.FRONT_BACK ? q.rotate() : q.rotateCC());
            };}
        }
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        if (rotation == BlockRotation.NONE) {return state;}
        switch (state.get(AXIS)) {
            case X -> {
                return switch (rotation) {
                    case COUNTERCLOCKWISE_90 -> state.with(AXIS,  Direction.Axis.Z);
                    case CLOCKWISE_90        -> mirror(state.with(AXIS,  Direction.Axis.Z), BlockMirror.FRONT_BACK);
                    default                  -> mirror(state, BlockMirror.LEFT_RIGHT);
                };}
            case Z -> {return switch (rotation) {
                    case CLOCKWISE_90        -> state.with(AXIS,  Direction.Axis.X);
                    case COUNTERCLOCKWISE_90 -> mirror(state.with(AXIS,  Direction.Axis.X), BlockMirror.LEFT_RIGHT);
                    default                  -> mirror(state, BlockMirror.FRONT_BACK);
                };}
            default -> {
                Quadrant q = state.get(QUADRANT);
                return switch (rotation) {
                    case CLOCKWISE_90        -> state.with(QUADRANT, q.rotate());
                    case COUNTERCLOCKWISE_90 -> state.with(QUADRANT, q.rotateCC());
                    default                  -> state.with(QUADRANT, q.rotate().rotate());
            };}
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(QUADRANT, AXIS, Properties.WATERLOGGED);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();
        BlockPos.Mutable v = ctx.getBlockPos().mutableCopy();
        {
            Vec3d v_ = ctx.getHitPos();
            int x = (int) (-1000*(0.5 + v.getX() - v_.x));
            int y = (int) (-1000*(0.5 + v.getY() - v_.y));
            int z = (int) (-1000*(0.5 + v.getZ() - v_.z));
            v = v.set(x,y,z);
        }
        switch (side.getAxis()) {
            case X -> v.setX(0);
            case Y -> v.setY(0);
            case Z -> v.setZ(0);
        }
        String q;
        Direction.Axis axis = Direction.Axis.Y;
        {
            Direction d = Direction.fromVector(v, Direction.UP);
            boolean a = side.getOpposite().getDirection() == Direction.AxisDirection.POSITIVE;
            boolean b = d.getDirection() == Direction.AxisDirection.POSITIVE;
            Direction.Axis axis_a = side.getAxis();
            Direction.Axis axis_b = d.getAxis();

            for (Direction.Axis A : Direction.Axis.values()) {
                if (A != axis_a && A != axis_b) {axis = A;}
            }
            if (a != b && axis_a != Direction.Axis.Z && axis_b != Direction.Axis.Y) {
                boolean x = a;
                a = b;
                b = x;
            }
            q = (a ? "P" : "N") + (b ? "P" : "N");
        }
        return this.getDefaultState().with(QUADRANT, Quadrant.valueOf(q)).with(AXIS, axis);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = state.get(QUADRANT).ordinal();
        switch (state.get(AXIS)) {
            case X  -> {return X[i];}
            case Z  -> {return Z[i];}
            default -> {return Y[i];}
        }
    }
    static {
        VoxelShape top = Block.createCuboidShape(0, 8, 8 , 16, 16, 16);
        VoxelShape bot = Block.createCuboidShape(0, 0, 8 , 16, 8 , 16);
        VoxelShape y   = Block.createCuboidShape(8, 0, 8 , 16, 16, 16);
        X = new VoxelShape[]{
                top,
                VoxelTransform.rotate180(top),
                VoxelTransform.rotate180(bot),
                bot,
        };
        Z = new VoxelShape[]{
                VoxelTransform.rotate270(top),
                VoxelTransform.rotate90(top),
                VoxelTransform.rotate90(bot),
                VoxelTransform.rotate270(bot),
        };
        Y = new VoxelShape[]{
                y,
                VoxelTransform.rotate270(y),
                VoxelTransform.rotate180(y),
                VoxelTransform.rotate90(y)
        };
    }
}
