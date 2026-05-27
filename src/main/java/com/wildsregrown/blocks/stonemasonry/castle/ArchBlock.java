package com.wildsregrown.blocks.stonemasonry.castle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;

public class ArchBlock extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape[] OUTLINE_SHAPES;
    private static final VoxelShape[] SHAPES;

    public ArchBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X).setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.HALF, Half.TOP));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {

        switch (rotation) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> {return state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X ? state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z) : state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);}
            default -> {return state;}
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.WATERLOGGED, BlockStateProperties.HALF);
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return super.getStateDefinition();
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER, pos));
        }
        return super.updateShape(state, world, tickView, pos,direction, neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        boolean waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        BlockState state = defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged);

        if (ctx.getClickedFace() == Direction.UP || (ctx.getClickedPos().getY() + 0.5 - ctx.getClickLocation().y) > 0) {
            state = state.setValue(BlockStateProperties.HALF, Half.BOTTOM);
        }

        return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, ctx.getHorizontalDirection().getAxis());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int i = state.getValue(BlockStateProperties.HALF) == Half.TOP ? 0 : 2;
        return state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.Z ? OUTLINE_SHAPES[i] : OUTLINE_SHAPES[i+1];
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state) {
        int i = state.getValue(BlockStateProperties.HALF) == Half.TOP ? 0 : 2;
        return state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.Z ? SHAPES[i] : SHAPES[i+1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getOcclusionShape(state);
    }

    static {
        VoxelShape shape = Shapes.or(
                Block.box(0     , 11  , 0    , 16   , 16   , 16),
                Block.box(6     , 11  , -1   , 10   , 15.75, 17),
                Block.box(8.8   , 11  , -0.92, 14.28, 13.2 , 16.92),
                Block.box(-4    , 0   , -0.92, 0.025, 5.35 , 16.92),
                Block.box(-2.5  , 5.35, -0.92, 0.86 , 9    , 16.92),
                Block.box(0     , 9   , -0.92, 3    , 11.4 , 16.92),
                Block.box(1.72  , 11  , -0.92, 7.2  , 13.2 , 16.92),
                Block.box(13    , 9   , -0.92, 16   , 11.4 , 16.92),
                Block.box(15.14 , 5.35, -0.92, 18.5 , 9    , 16.92),
                Block.box(15.975, 0   , -0.92, 20   , 5.35 , 16.92));
        OUTLINE_SHAPES = new VoxelShape[] {
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.mirrorY(shape),
                VoxelTransform.rotate270(VoxelTransform.mirrorY(shape))
        };
        SHAPES = new VoxelShape[] {
                Shapes.joinUnoptimized(Shapes.block(), OUTLINE_SHAPES[0], BooleanOp.AND),
                Shapes.joinUnoptimized(Shapes.block(), OUTLINE_SHAPES[1], BooleanOp.AND),
                Shapes.joinUnoptimized(Shapes.block(), OUTLINE_SHAPES[2], BooleanOp.AND),
                Shapes.joinUnoptimized(Shapes.block(), OUTLINE_SHAPES[3], BooleanOp.AND)
        };
    }
}
