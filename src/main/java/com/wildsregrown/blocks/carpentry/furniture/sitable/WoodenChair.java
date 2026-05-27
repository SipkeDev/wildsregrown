package com.wildsregrown.blocks.carpentry.furniture.sitable;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenChair extends AbstractSitable implements ITintedBlock {

    private static final Map<Direction, VoxelShape> basic_shapes;
    private static final Map<Direction, VoxelShape> refined_shapes;
    private static final Map<Direction, VoxelShape> throne_shapes;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final int tier;

    public WoodenChair(Properties settings, int tier, double height){
        super(settings, height);
        this.tier = tier;
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE).setValue(WATERLOGGED, false));
    }


    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (tier) {
            case 1 -> refined_shapes.get(state.getValue(FACING));
            case 2 -> throne_shapes.get(state.getValue(FACING));
            default -> basic_shapes.get(state.getValue(FACING));
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        basic_shapes = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.125, 0, 0.125, 0.25, 0.3125, 0.3125),
                Shapes.box(0.125, 0.3125, 0.125, 0.875, 0.4375, 0.6875),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.3125, 0.3125),
                Shapes.box(0.125, 0, 0.6875, 0.25, 1, 0.875),
                Shapes.box(0.75, 0, 0.6875, 0.875, 1, 0.875),
                Shapes.box(0.25, 0.3125, 0.6875, 0.75, 0.9375, 0.875)
        ));
        refined_shapes = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.125, 0, 0.125, 0.25, 0.625, 0.3125),
                Shapes.box(0.25, 0.3125, 0.1875, 0.75, 0.4375, 0.6875),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.625, 0.3125),
                Shapes.box(0.125, 0, 0.6875, 0.25, 1, 0.875),
                Shapes.box(0.75, 0, 0.6875, 0.875, 1, 0.875),
                Shapes.box(0.25, 0.375, 0.6875, 0.75, 0.875, 0.875),
                Shapes.box(0.75, 0.3125, 0.3125, 0.875, 0.5625, 0.6875),
                Shapes.box(0.25, 0.3125, 0.6875, 0.75, 0.375, 0.875),
                Shapes.box(0.25, 0.875, 0.6875, 0.75, 0.9375, 0.875),
                Shapes.box(0.25, 0.3125, 0.125, 0.75, 0.4375, 0.1875),
                Shapes.box(0.125, 0.3125, 0.3125, 0.25, 0.5625, 0.6875)
        ));
        throne_shapes = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 0.8125, 1),
                Shapes.box(0.875, 0, 0, 1, 0.8125, 1),
                Shapes.box(0.125, 0, 0.0625, 0.875, 0.5, 1),
                Shapes.box(-0.03125, 0.78125, -0.03125, 0.15625, 0.84375, 0.96875),
                Shapes.box(0.84375, 0.78125, -0.03125, 1.03125, 0.84375, 0.96875),
                Shapes.box(0.875, 0.8125, 0.875, 1, 1.5, 1),
                Shapes.box(0, 0.8125, 0.875, 0.125, 1.5, 1),
                Shapes.box(0.125, 0.5, 0.875, 0.875, 1.5, 1)
        ));
    }
}
