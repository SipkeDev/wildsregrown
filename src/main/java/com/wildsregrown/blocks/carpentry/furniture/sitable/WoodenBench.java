package com.wildsregrown.blocks.carpentry.furniture.sitable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.render.ITintedBlock;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class WoodenBench extends AbstractSitableBench implements ITintedBlock {

    private static final Map<Direction, VoxelShape> basic_single;
    private static final Map<Direction, VoxelShape> basic_middle;
    private static final Map<Direction, VoxelShape> basic_left;
    private static final Map<Direction, VoxelShape> basic_right;

    private static final Map<Direction, VoxelShape> refined_single;
    private static final Map<Direction, VoxelShape> refined_middle;
    private static final Map<Direction, VoxelShape> refined_left;
    private static final Map<Direction, VoxelShape> refined_right;

    private final int tier;

    public WoodenBench(Properties settings, int tier) {
        super(settings, 0.45f, tier == 0 ? 0.12 : 0.08);
        this.tier = tier;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)) {
            case SINGLE -> switch (tier) {
                case 1 -> refined_single.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> basic_single.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_single.get(state.getValue(HORIZONTAL_FACING));
            };
            case MIDDLE -> switch (tier) {
                case 1 -> refined_middle.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> basic_middle.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_middle.get(state.getValue(HORIZONTAL_FACING));
            };
            case LEFT -> switch (tier) {
                case 1 -> refined_left.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> basic_left.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_left.get(state.getValue(HORIZONTAL_FACING));
            };
            case RIGHT -> switch (tier) {
                case 1 -> refined_right.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> basic_right.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_right.get(state.getValue(HORIZONTAL_FACING));
            };
        };
    }

    static {
        basic_single = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0.25, 0.09375, 0.375, 0.75),
                Shapes.box(0.90625, 0, 0.25, 1, 0.375, 0.75),
                Shapes.box(0, 0.375, 0.25, 1, 0.5, 0.75)
        ));
        basic_middle = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.375, 0.25, 1, 0.5, 0.75)
        ));
        basic_right = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0.25, 0.09375, 0.375, 0.75),
                Shapes.box(0, 0.375, 0.25, 1, 0.5, 0.75)
        ));
        basic_left = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.90625, 0, 0.25, 1, 0.375, 0.75),
                Shapes.box(0, 0.375, 0.25, 1, 0.5, 0.75)
        ));


        refined_single = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0.0625, 0.1875, 0.34375, 0.1875, 0.25, 0.65625),
                Shapes.box(0.0625, 0.3125, 0.25, 0.1875, 0.375, 0.75),
                Shapes.box(0.0625, 0.0625, 0.25, 0.1875, 0.125, 0.75),
                Shapes.box(0.0625, 0.125, 0.3125, 0.1875, 0.1875, 0.6875),
                Shapes.box(0.0625, 0.25, 0.3125, 0.1875, 0.3125, 0.6875),
                Shapes.box(0.03125, 0, 0.25, 0.21875, 0.0625, 0.375),
                Shapes.box(0.03125, 0, 0.625, 0.21875, 0.0625, 0.75),
                Shapes.box(0.03125, 0.175, 0.46875, 0.96875, 0.26875, 0.5625),
                Shapes.box(0.78125, 0, 0.25, 0.96875, 0.0625, 0.375),
                Shapes.box(0.78125, 0, 0.625, 0.96875, 0.0625, 0.75),
                Shapes.box(0.8125, 0.0625, 0.25, 0.9375, 0.125, 0.75),
                Shapes.box(0.8125, 0.125, 0.3125, 0.9375, 0.1875, 0.6875),
                Shapes.box(0.8125, 0.25, 0.3125, 0.9375, 0.3125, 0.6875),
                Shapes.box(0.8125, 0.1875, 0.34375, 0.9375, 0.25, 0.65625),
                Shapes.box(0.8125, 0.3125, 0.25, 0.9375, 0.375, 0.75)
        ));
        refined_middle = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0, 0.175, 0.46875, 1, 0.26875, 0.5625)
        ));
        refined_right = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0, 0.175, 0.46875, 0.96875, 0.26875, 0.5625),
                Shapes.box(0.75, 0, 0.25, 0.9375, 0.0625, 0.375),
                Shapes.box(0.75, 0, 0.625, 0.9375, 0.0625, 0.75),
                Shapes.box(0.78125, 0.0625, 0.25, 0.90625, 0.125, 0.75),
                Shapes.box(0.78125, 0.125, 0.3125, 0.90625, 0.1875, 0.6875),
                Shapes.box(0.78125, 0.1875, 0.34375, 0.90625, 0.25, 0.65625),
                Shapes.box(0.78125, 0.25, 0.3125, 0.90625, 0.3125, 0.6875),
                Shapes.box(0.78125, 0.3125, 0.25, 0.90625, 0.375, 0.75)
        ));
        refined_left = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.375, 0.1875, 1, 0.5, 0.8125),
                Shapes.box(0.0875, 0.1875, 0.34375, 0.2125, 0.25, 0.65625),
                Shapes.box(0.0875, 0.3125, 0.25, 0.2125, 0.375, 0.75),
                Shapes.box(0.0875, 0.0625, 0.25, 0.2125, 0.125, 0.75),
                Shapes.box(0.0875, 0.125, 0.3125, 0.2125, 0.1875, 0.6875),
                Shapes.box(0.0875, 0.25, 0.3125, 0.2125, 0.3125, 0.6875),
                Shapes.box(0.05625, 0, 0.25, 0.24375, 0.0625, 0.375),
                Shapes.box(0.05625, 0, 0.625, 0.24375, 0.0625, 0.75),
                Shapes.box(0.03125, 0.175, 0.46875, 1, 0.26875, 0.5625)
        ));

    }

}
