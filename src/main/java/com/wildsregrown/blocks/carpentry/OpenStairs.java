package com.wildsregrown.blocks.carpentry;

import com.mojang.math.OctahedralGroup;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

import java.util.Map;

public class OpenStairs extends StairBlock implements ITintedBlock {

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    private static final VoxelShape OUTER_SHAPE;
    private static final VoxelShape STRAIGHT_SHAPE;
    private static final VoxelShape INNER_SHAPE;
    private static final Map<Direction, VoxelShape> OUTER_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> STRAIGHT_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> INNER_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> OUTER_TOP_SHAPES;
    private static final Map<Direction, VoxelShape> STRAIGHT_TOP_SHAPES;
    private static final Map<Direction, VoxelShape> INNER_TOP_SHAPES;

    public OpenStairs(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean bl = state.getValue(HALF) == Half.BOTTOM;
        Direction direction = state.getValue(FACING);
        Map<Direction, VoxelShape> map = switch (state.getValue(SHAPE)) {
            case STRAIGHT -> bl ? STRAIGHT_BOTTOM_SHAPES : STRAIGHT_TOP_SHAPES;
            case OUTER_LEFT, OUTER_RIGHT -> bl ? OUTER_BOTTOM_SHAPES : OUTER_TOP_SHAPES;
            case INNER_RIGHT, INNER_LEFT -> bl ? INNER_BOTTOM_SHAPES : INNER_TOP_SHAPES;
            default -> throw new MatchException(null, null);
        };

        Direction dir = switch (state.getValue(SHAPE)) {
            case STRAIGHT, OUTER_LEFT, INNER_RIGHT -> direction;
            case INNER_LEFT -> direction.getCounterClockWise();
            case OUTER_RIGHT -> direction.getClockWise();
            default -> throw new MatchException(null, null);
        };

        return map.get(dir);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        OUTER_SHAPE = VoxelTransform.rotate270(Shapes.or(
                Shapes.box(0, 0.1875, 0, 0.5, 0.4375, 1),
                Shapes.box(0.5, 0.6875, 0.5, 1, 0.9375, 1),
                Shapes.box(0.5, 0.1875, 0, 1, 0.4375, 0.5)
        ));
        STRAIGHT_SHAPE = VoxelTransform.rotate270(Shapes.or(
                Shapes.box(0, 0.1875, 0, 0.5, 0.4375, 1),
                Shapes.box(0.5, 0.6875, 0, 1, 0.9375, 1)
        ));
        INNER_SHAPE = VoxelTransform.rotate270(Shapes.or(
                Shapes.box(0, 0.1875, 0, 0.5, 0.4375, 0.5),
                Shapes.box(0.5, 0.6875, 0, 1, 0.9375, 0.5),
                Shapes.box(0, 0.6875, 0.5, 1, 0.9375, 1)
        ));
        OUTER_BOTTOM_SHAPES = Shapes.rotateHorizontal(OUTER_SHAPE);
        STRAIGHT_BOTTOM_SHAPES = Shapes.rotateHorizontal(STRAIGHT_SHAPE);
        INNER_BOTTOM_SHAPES = Shapes.rotateHorizontal(INNER_SHAPE);
        OUTER_TOP_SHAPES = Shapes.rotateHorizontal(OUTER_SHAPE, OctahedralGroup.INVERT_Y);
        STRAIGHT_TOP_SHAPES = Shapes.rotateHorizontal(STRAIGHT_SHAPE, OctahedralGroup.INVERT_Y);
        INNER_TOP_SHAPES = Shapes.rotateHorizontal(INNER_SHAPE, OctahedralGroup.INVERT_Y);
    }

}
