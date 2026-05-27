package com.wildsregrown.blocks.carpentry.framing.beam;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.Quadrant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import wildsregrown.api.block.VoxelTransform;

public class BeamSupport extends AbstractSupport {

    private static final VoxelShape[] X;
    private static final VoxelShape[] Y;
    private static final VoxelShape[] Z;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final EnumProperty<Quadrant> QUADRANT = ModProperties.QUADRANT;

    public BeamSupport(String id, Properties settings) {
        super(id, settings);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(QUADRANT, Quadrant.I).setValue(BlockStateProperties.WATERLOGGED, false).setValue(PAINT, LinSeedPaintable.NONE));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER, pos));
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE) {return state;}
        Quadrant q = state.getValue(QUADRANT);

        switch (state.getValue(AXIS)) {
            case X -> {return mirror == Mirror.LEFT_RIGHT ? state.setValue(QUADRANT, q.mirrorH()) : state;}
            case Z -> {return mirror == Mirror.FRONT_BACK ? state.setValue(QUADRANT, q.mirrorH()) : state;}
            default-> {return switch (q) {
                case II, IV -> state.setValue(QUADRANT, mirror == Mirror.LEFT_RIGHT ? q.rotate() : q.rotateCC());
                case I, III -> state.setValue(QUADRANT, mirror == Mirror.FRONT_BACK ? q.rotate() : q.rotateCC());
            };}
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        if (rotation == Rotation.NONE) {return state;}
        switch (state.getValue(AXIS)) {
            case X -> {
                return switch (rotation) {
                    case COUNTERCLOCKWISE_90 -> state.setValue(AXIS,  Direction.Axis.Z);
                    case CLOCKWISE_90        -> mirror(state.setValue(AXIS,  Direction.Axis.Z), Mirror.FRONT_BACK);
                    default                  -> mirror(state, Mirror.LEFT_RIGHT);
                };}
            case Z -> {return switch (rotation) {
                case CLOCKWISE_90        -> state.setValue(AXIS,  Direction.Axis.X);
                case COUNTERCLOCKWISE_90 -> mirror(state.setValue(AXIS,  Direction.Axis.X), Mirror.LEFT_RIGHT);
                default                  -> mirror(state, Mirror.FRONT_BACK);
            };}
            default -> {
                Quadrant q = state.getValue(QUADRANT);
                return switch (rotation) {
                    case CLOCKWISE_90        -> state.setValue(QUADRANT, q.rotate());
                    case COUNTERCLOCKWISE_90 -> state.setValue(QUADRANT, q.rotateCC());
                    default                  -> state.setValue(QUADRANT, q.rotate().rotate());
                };}
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, QUADRANT, AXIS, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction side = ctx.getClickedFace();
        BlockPos.MutableBlockPos blockPos = ctx.getClickedPos().mutable();
        {
            Vec3 vec = ctx.getClickLocation();
            int x = (int) (-1000*(0.5 + blockPos.getX() - vec.x));
            int y = (int) (-1000*(0.5 + blockPos.getY() - vec.y));
            int z = (int) (-1000*(0.5 + blockPos.getZ() - vec.z));
            blockPos = blockPos.set(x,y,z);
        }
        switch (side.getAxis()) {
            case X -> blockPos.setX(0);
            case Y -> blockPos.setY(0);
            case Z -> blockPos.setZ(0);
        }
        Quadrant quadrant;
        Direction.Axis axis = Direction.Axis.Y;
        {
            Direction d = Direction.getNearest(blockPos, Direction.UP);
            boolean a = side.getOpposite().getAxisDirection() == Direction.AxisDirection.POSITIVE;
            boolean b = d.getAxisDirection() == Direction.AxisDirection.POSITIVE;
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
            if (a && b){
                quadrant = Quadrant.I;
            }else if (!a && b){
                quadrant = Quadrant.II;
            }else if(!a && !b){
                quadrant = Quadrant.III;
            }else {
                quadrant = Quadrant.IV;
            }
        }
        return this.defaultBlockState().setValue(QUADRANT, quadrant).setValue(AXIS, axis);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int i = state.getValue(QUADRANT).ordinal();
        switch (state.getValue(AXIS)) {
            case X  -> {return X[i];}
            case Z  -> {return Z[i];}
            default -> {return Y[i];}
        }
    }
    static {
        VoxelShape top = Block.box(0, 8, 8 , 16, 16, 16);
        VoxelShape bot = Block.box(0, 0, 8 , 16, 8 , 16);
        VoxelShape y   = Block.box(8, 0, 8 , 16, 16, 16);
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

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }
}
