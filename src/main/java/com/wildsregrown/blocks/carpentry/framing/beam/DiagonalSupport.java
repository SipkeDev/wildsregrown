package com.wildsregrown.blocks.carpentry.framing.beam;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.beam.SupportDiagonal;
import com.wildsregrown.blocks.properties.framing.beam.SupportPost;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class DiagonalSupport extends AbstractSupport {

    private static final Map<Direction, VoxelShape> shape;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    public static final EnumProperty<SupportDiagonal> SHAPE = EnumProperty.create("shape", SupportDiagonal.class);
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public DiagonalSupport(String id, Properties settings) {
        super(id, settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, SHAPE, FACING, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState currentState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (isOfPost(currentState)){
            if (currentState.getValue(PostSupport.SHAPE) == SupportPost.CEILING_BRACKET){
                return currentState.setValue(PostSupport.SHAPE, SupportPost.CEILING_DIAGONAL);
            }
        }
        return getShape(this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()), ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return getShape(state, world, pos);
    }

    private static BlockState getShape(BlockState state, LevelReader world, BlockPos pos) {

        Direction direction = state.getValue(FACING);
        BlockState standingOn = world.getBlockState(pos.below());
        BlockState under = world.getBlockState(pos.relative(direction).below());
        BlockState above = world.getBlockState(pos.relative(direction.getOpposite()).above());

        if (isOfPost(under) && isOfCeiling(above)){
            return state.setValue(SHAPE, SupportDiagonal.DIAGONAL_BRACKET);
        }else if (isOfPost(standingOn)) {
            return state.setValue(SHAPE, SupportDiagonal.DIAGONAL_ON_POST);
        }if (isOfCeiling(above)){
            return state.setValue(SHAPE, SupportDiagonal.DIAGONAL_CEILING);
        }else
        if (isOfPost(under)){
            return state.setValue(SHAPE, SupportDiagonal.DIAGONAL_POST);
        }else {
            return state.setValue(SHAPE, SupportDiagonal.DIAGONAL);
        }

    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape.get(state.getValue(FACING));
    }

    static {
        shape = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.25, 0, 0.8125, 0.75, 0.8125, 1),
                Shapes.box(0.25, 0.1875, 0.625, 0.75, 1, 0.8125),
                Shapes.box(0.25, 0.375, 0.4375, 0.75, 1.1875, 0.625),
                Shapes.box(0.25, 0.5625, 0.25, 0.75, 1.375, 0.4375),
                Shapes.box(0.25, 0.75, 0, 0.75, 1.5625, 0.25)
        )));
    }

}
