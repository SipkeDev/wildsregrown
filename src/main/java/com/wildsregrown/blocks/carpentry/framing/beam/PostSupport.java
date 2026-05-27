package com.wildsregrown.blocks.carpentry.framing.beam;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.beam.SupportPost;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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

public class PostSupport extends AbstractSupport {

    private static final Map<Direction, VoxelShape> post_shape;
    private static final Map<Direction, VoxelShape> bracket_shape;
    private static final Map<Direction, VoxelShape> diagonal_shape;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    public static final EnumProperty<SupportPost> SHAPE = EnumProperty.create("shape", SupportPost.class);
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PostSupport(String id, Properties settings) {
        super(id, settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, SHAPE, FACING, WATERLOGGED);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (context.getItemInHand().getItem() instanceof FramingBeamItem beamItem) {
            Block block = beamItem.getBlock(context.getItemInHand());
            if (isOfCeiling(block)) {
                WildsRegrown.LOGGER.info("HELLOOOO");
                return true;
            }
            if (state.getValue(SHAPE) == SupportPost.CEILING_BRACKET && isOfDiagonal(block)) {
                return true;
            }
        }
        return super.canBeReplaced(state, context);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction dir = ctx.getHorizontalDirection();
        BlockState state = defaultBlockState()
                .setValue(FACING, dir)
                .setValue(SHAPE, SupportPost.BEAM);

        if (isOfCeiling(ctx.getLevel().getBlockState(ctx.getClickedPos()))){
            WildsRegrown.LOGGER.info("POst placement on ceiling");
            return state.setValue(PostSupport.SHAPE, SupportPost.CEILING_BRACKET);
        }
        return state;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)){
            case BEAM -> post_shape.get(state.getValue(FACING));
            case CEILING_BRACKET -> bracket_shape.get(state.getValue(FACING));
            case CEILING_DIAGONAL -> diagonal_shape.get(state.getValue(FACING));
        };
    }

    static {
        post_shape = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.25, 0, 0.5, 0.75, 1, 1)
        )));
        bracket_shape = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.25, 0, 0.5, 0.75, 1, 1),
                Shapes.box(0.25, 0.5, 0, 0.75, 1, 0.5)
        )));
        diagonal_shape = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.25, 0, 0.5, 0.75, 1, 1),
                Shapes.box(0.25, 0.5, 0, 0.75, 1, 0.5),
                Shapes.box(0.3125, 0.125, -0.0625, 0.6875, 0.5, 0.25),
                Shapes.box(0.3125, -0.3125, 0.1875, 0.6875, 0.0625, 0.5),
                Shapes.box(0.3125, -0.0625, 0.0625, 0.6875, 0.25, 0.375)
        )));
    }

}
