package com.wildsregrown.blocks.stone;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

public class Corner extends Block implements Waterloggable {

    private static final VoxelShape[][][] SHAPES;

    public static final BooleanProperty X = BooleanProperty.of("x");
    public static final BooleanProperty Y = BooleanProperty.of("y");
    public static final BooleanProperty Z = BooleanProperty.of("z");
    public Corner(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(X, true).with(Y, false).with(Z, true).with(Properties.WATERLOGGED, false));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {return state.with(Z, !state.get(Z));}
            case FRONT_BACK -> {return state.with(X, !state.get(X));}
            default -> {return state;}
        }
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        boolean x = state.get(X);
        boolean z = state.get(Z);
        switch (rotation) {
            case CLOCKWISE_90       -> {return x == z ? state.with(X, !x) : state.with(Z, !z);}
            case CLOCKWISE_180      -> {return state.with(Z, !z).with(X, !x);}
            case COUNTERCLOCKWISE_90-> {return x == z ? state.with(Z, !z) : state.with(X, !x);}
            default -> {return state;}
        }
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

//    public BlockState rotate(BlockState state, BlockRotation rotation) {
//        return state.with(FACING, rotation.rotate(state.get(FACING)));
//    }
//    public BlockState mirror(BlockState state, BlockMirror mirror) {
//        return state.rotate(mirror.getRotation(state.get(FACING)));
//    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Y,X,Z, Properties.WATERLOGGED);
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos.Mutable v = ctx.getBlockPos().mutableCopy();
        Vec3d v_ = ctx.getHitPos();

        boolean x = (0.5 + v.getX() - v_.x) < 0;
        boolean y = (0.5 + v.getY() - v_.y) < 0;
        boolean z = (0.5 + v.getZ() - v_.z) < 0;
        return this.getDefaultState().with(X, x).with(Y, y).with(Z, z).with(Properties.WATERLOGGED, false);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(X) ? 1 : 0][state.get(Z) ? 1 : 0][state.get(Y) ? 1 : 0];
    }


    static {
        VoxelShape top = Block.createCuboidShape(8, 8, 8 , 16, 16, 16);
        VoxelShape bot = Block.createCuboidShape(8, 0, 8 , 16, 8, 16);

        VoxelShape[] SHAPE_PP = new VoxelShape[]{bot, top};
        VoxelShape[] SHAPE_NP = new VoxelShape[]{VoxelTransform.rotate90 (bot), VoxelTransform.rotate90 (top)};
        VoxelShape[] SHAPE_NN = new VoxelShape[]{VoxelTransform.rotate180(bot), VoxelTransform.rotate180(top)};
        VoxelShape[] SHAPE_PN = new VoxelShape[]{VoxelTransform.rotate270(bot), VoxelTransform.rotate270(top)};

        SHAPES = new VoxelShape[/*X*/][/*Z*/][/*Y*/]{
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
