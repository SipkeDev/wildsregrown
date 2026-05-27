package com.wildsregrown.blocks.stone_old;

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
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import wildsregrown.api.block.VoxelTransform;

public class Notch extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape[][][][] SHAPES;

    public static final BooleanProperty X = BooleanProperty.create("x");
    public static final BooleanProperty Y = BooleanProperty.create("y");
    public static final BooleanProperty Z = BooleanProperty.create("z");
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public Notch(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(X, true).setValue(Y, false).setValue(AXIS, Direction.Axis.Y).setValue(Z, true).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        if (rotation == Rotation.NONE) {
            return state;
        }

        Direction.Axis axis = state.getValue(AXIS);
        boolean x = state.getValue(X);
        boolean z = state.getValue(Z);

        if (rotation == Rotation.CLOCKWISE_180) {
            return state.setValue(X, !x).setValue(Z, !z);
        }

        if (axis == Direction.Axis.X) {
            state = state.setValue(AXIS, Direction.Axis.Z);
        }
        if (axis == Direction.Axis.Z) {
            state = state.setValue(AXIS, Direction.Axis.X);
        }

        if ((rotation == Rotation.CLOCKWISE_90) == (x == z)) {
            return state.setValue(X, !x);
        }
        return state.setValue(Z, !z);
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {return state.setValue(Z, !state.getValue(Z));}
            case FRONT_BACK -> {return state.setValue(X, !state.getValue(X));}
            default -> {return state;}
        }
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER, pos));
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

//    public BlockState rotate(BlockState state, BlockRotation rotation) {
//        return state.with(FACING, rotation.rotate(state.get(FACING)));
//    }
//    public BlockState mirror(BlockState state, BlockMirror mirror) {
//        return state.rotate(mirror.getRotation(state.get(FACING)));
//    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(Y,X,Z, AXIS, BlockStateProperties.WATERLOGGED);
    }
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos.MutableBlockPos v = ctx.getClickedPos().mutable();
        Vec3 v_ = ctx.getClickLocation();

        boolean x = (0.5 + v.getX() - v_.x) < 0;
        boolean y = (0.5 + v.getY() - v_.y) < 0;
        boolean z = (0.5 + v.getZ() - v_.z) < 0;

        return this.defaultBlockState().setValue(X, x).setValue(Y, y).setValue(Z, z).setValue(AXIS, ctx.getClickedFace().getAxis()).setValue(BlockStateProperties.WATERLOGGED, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(X) ? 1 : 0][state.getValue(Z) ? 1 : 0][state.getValue(Y) ? 1 : 0][state.getValue(AXIS).ordinal()];
    }


    static {
        VoxelShape topX = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(8 , 8, 0, 16, 16, 16));
        VoxelShape topY = Shapes.or(
                Block.box(8 , 8, 0, 16, 16, 16),
                Block.box(0 , 8, 8, 16, 16, 16));
        VoxelShape topZ = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(0 , 8, 8, 16, 16, 16));

        VoxelShape botX = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(8 , 0, 0, 16, 8, 16));
        VoxelShape botY = Shapes.or(
                Block.box(8 , 0, 0, 16, 8, 16),
                Block.box(0 , 0, 8, 16, 8, 16));
        VoxelShape botZ = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(0 , 0, 8, 16, 8, 16));


        //VoxelShape[][] SHAPE_PP = new VoxelShape[]{bot, top};
        VoxelShape[][] SHAPE_PP = new VoxelShape[][]{{botX,botY,botZ}, {topX,topY,topZ}};
        VoxelShape[][] SHAPE_NP = new VoxelShape[][]{{VoxelTransform.rotate90 (botZ), VoxelTransform.rotate90(botY), VoxelTransform.rotate90(botX)},   {VoxelTransform.rotate90(topZ), VoxelTransform.rotate90(topY), VoxelTransform.rotate90(topX)}};
        VoxelShape[][] SHAPE_NN = new VoxelShape[][]{{VoxelTransform.rotate180(botX), VoxelTransform.rotate180(botY), VoxelTransform.rotate180(botZ)}, {VoxelTransform.rotate180(topX), VoxelTransform.rotate180(topY), VoxelTransform.rotate180(topZ)}};
        VoxelShape[][] SHAPE_PN = new VoxelShape[][]{{VoxelTransform.rotate270(botZ), VoxelTransform.rotate270(botY), VoxelTransform.rotate270(botX)}, {VoxelTransform.rotate270(topZ), VoxelTransform.rotate270(topY), VoxelTransform.rotate270(topX)}};

        SHAPES = new VoxelShape[/*X*/][/*Z*/][/*Y*/][/*AXIS*/]{
                {
                        SHAPE_NN,
                        SHAPE_NP
                },
                {
                        SHAPE_PN,
                        SHAPE_PP
                }
        };
    }
}
