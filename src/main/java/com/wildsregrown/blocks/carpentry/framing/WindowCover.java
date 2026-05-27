package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class WindowCover extends Block implements SimpleWaterloggedBlock, ITintedBlock {

    private static final VoxelShape[] SHAPES;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final IntegerProperty VARIATIONS = ModProperties.VARIATIONS_4;

    public WindowCover(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(VARIATIONS, 1).setValue(PAINT, LinSeedPaintable.NONE).setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIATIONS, PAINT, FACING, BlockStateProperties.WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(BlockStateProperties.WATERLOGGED, waterlogged);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            default    -> {return SHAPES[0];}
            case EAST  -> {return SHAPES[1];}
            case NORTH -> {return SHAPES[2];}
            case WEST  -> {return SHAPES[3];}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        VoxelShape shape = Shapes.or(
                Shapes.box(0.5, 0.0625, 0, 0.9375, 0.9375, 0.125),
                Shapes.box(0, 0, 0, 1, 0.0625, 0.3125),
                Shapes.box(0, 0.9375, 0, 1, 1, 0.3125),
                Shapes.box(0, 0.0625, 0, 0.0625, 0.9375, 0.3125),
                Shapes.box(0.9375, 0.0625, 0, 1, 0.9375, 0.3125),
                Shapes.box(0.4375, 0.0625, 0.125, 0.5, 0.9375, 0.1875),
                Shapes.box(0.5, 0.0625, 0.125, 0.5625, 0.9375, 0.1875),
                Shapes.box(0.0625, 0.0625, 0, 0.5, 0.9375, 0.125)
        );
        SHAPES = new VoxelShape[]{
                shape,
                VoxelTransform.rotate90(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate270(shape),
        };
    }
}
