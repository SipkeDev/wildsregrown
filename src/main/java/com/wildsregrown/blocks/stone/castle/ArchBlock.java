package com.wildsregrown.blocks.stone.castle;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class ArchBlock extends Block implements Waterloggable {

    private static final VoxelShape[] OUTLINE_SHAPES;
    private static final VoxelShape[] SHAPES;

    public ArchBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_AXIS, Direction.Axis.X).with(Properties.WATERLOGGED, false).with(Properties.BLOCK_HALF, BlockHalf.TOP));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {

        switch (rotation) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> {return state.get(Properties.HORIZONTAL_AXIS) == Direction.Axis.X ? state.with(Properties.HORIZONTAL_AXIS, Direction.Axis.Z) : state.with(Properties.HORIZONTAL_AXIS, Direction.Axis.X);}
            default -> {return state;}
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_AXIS, Properties.WATERLOGGED, Properties.BLOCK_HALF);
    }

    @Override
    public StateManager<Block, BlockState> getStateManager() {
        return super.getStateManager();
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos,direction, neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        boolean waterlogged = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        BlockState state = getDefaultState().with(Properties.WATERLOGGED, waterlogged);

        if (ctx.getSide() == Direction.UP || (ctx.getBlockPos().getY() + 0.5 - ctx.getHitPos().y) > 0) {
            state = state.with(Properties.BLOCK_HALF, BlockHalf.BOTTOM);
        }

        return state.with(Properties.HORIZONTAL_AXIS, ctx.getHorizontalPlayerFacing().getAxis());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        int i = state.get(Properties.BLOCK_HALF) == BlockHalf.TOP ? 0 : 2;
        return state.get(Properties.HORIZONTAL_AXIS) == Direction.Axis.Z ? OUTLINE_SHAPES[i] : OUTLINE_SHAPES[i+1];
    }

    @Override
    public VoxelShape getCullingShape(BlockState state) {
        int i = state.get(Properties.BLOCK_HALF) == BlockHalf.TOP ? 0 : 2;
        return state.get(Properties.HORIZONTAL_AXIS) == Direction.Axis.Z ? SHAPES[i] : SHAPES[i+1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getCullingShape(state);
    }

    static {
        VoxelShape shape = VoxelShapes.union(
                Block.createCuboidShape(0     , 11  , 0    , 16   , 16   , 16),
                Block.createCuboidShape(6     , 11  , -1   , 10   , 15.75, 17),
                Block.createCuboidShape(8.8   , 11  , -0.92, 14.28, 13.2 , 16.92),
                Block.createCuboidShape(-4    , 0   , -0.92, 0.025, 5.35 , 16.92),
                Block.createCuboidShape(-2.5  , 5.35, -0.92, 0.86 , 9    , 16.92),
                Block.createCuboidShape(0     , 9   , -0.92, 3    , 11.4 , 16.92),
                Block.createCuboidShape(1.72  , 11  , -0.92, 7.2  , 13.2 , 16.92),
                Block.createCuboidShape(13    , 9   , -0.92, 16   , 11.4 , 16.92),
                Block.createCuboidShape(15.14 , 5.35, -0.92, 18.5 , 9    , 16.92),
                Block.createCuboidShape(15.975, 0   , -0.92, 20   , 5.35 , 16.92));
        OUTLINE_SHAPES = new VoxelShape[] {
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.mirrorY(shape),
                VoxelTransform.rotate270(VoxelTransform.mirrorY(shape))
        };
        SHAPES = new VoxelShape[] {
                VoxelShapes.combine(VoxelShapes.fullCube(), OUTLINE_SHAPES[0], BooleanBiFunction.AND),
                VoxelShapes.combine(VoxelShapes.fullCube(), OUTLINE_SHAPES[1], BooleanBiFunction.AND),
                VoxelShapes.combine(VoxelShapes.fullCube(), OUTLINE_SHAPES[2], BooleanBiFunction.AND),
                VoxelShapes.combine(VoxelShapes.fullCube(), OUTLINE_SHAPES[3], BooleanBiFunction.AND)
        };
    }
}
