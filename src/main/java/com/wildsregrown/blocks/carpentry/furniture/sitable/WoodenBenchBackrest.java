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

public class WoodenBenchBackrest extends AbstractSitableBench implements ITintedBlock {

    /// basic shapes
    private static final Map<Direction, VoxelShape> basic_single;
    private static final Map<Direction, VoxelShape> basic_middle;
    private static final Map<Direction, VoxelShape> basic_left;
    private static final Map<Direction, VoxelShape> basic_right;
    
    /// refined shapes
    private static final Map<Direction, VoxelShape> refined_single;
    private static final Map<Direction, VoxelShape> refined_middle;
    private static final Map<Direction, VoxelShape> refined_left;
    private static final Map<Direction, VoxelShape> refined_right;
    
    /// luxury shapes
    private static final Map<Direction, VoxelShape> luxury_single;
    private static final Map<Direction, VoxelShape> luxury_middle;
    private static final Map<Direction, VoxelShape> luxury_left;
    private static final Map<Direction, VoxelShape> luxury_right;

    private final int tier;

    public WoodenBenchBackrest(Properties settings, int tier){
        super(settings, 0.4, 0.215);
        this.tier = tier;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)) {
            case SINGLE -> switch (tier) {
                case 1 -> refined_single.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> luxury_single.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_single.get(state.getValue(HORIZONTAL_FACING));
            };
            case MIDDLE -> switch (tier) {
                case 1 -> refined_middle.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> luxury_middle.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_middle.get(state.getValue(HORIZONTAL_FACING));
            };
            case LEFT -> switch (tier) {
                case 1 -> refined_left.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> luxury_left.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_left.get(state.getValue(HORIZONTAL_FACING));
            };
            case RIGHT -> switch (tier) {
                case 1 -> refined_right.get(state.getValue(HORIZONTAL_FACING));
                case 2 -> luxury_right.get(state.getValue(HORIZONTAL_FACING));
                default -> basic_right.get(state.getValue(HORIZONTAL_FACING));
            };
        };
    }

    static {
        /// basic shapes
        basic_single = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 1, 0.1875),
                Shapes.box(0.875, 0, 0, 1, 1, 0.1875),
                Shapes.box(0, 0, 0.5625, 0.1875, 0.25, 0.75),
                Shapes.box(0.8125, 0, 0.5625, 1, 0.25, 0.75),
                Shapes.box(0, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0.125, 0.25, 0.0625, 0.875, 0.9375, 0.1875)
        ));
        basic_middle = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.25, 0.5625),
                Shapes.box(0.4375, 0, 0.0625, 0.5625, 0.25, 0.1875),
                Shapes.box(0, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0, 0.25, 0.0625, 1, 0.9375, 0.1875)
        ));
        basic_right = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.875, 0, 0, 1, 1, 0.1875),
                Shapes.box(0.8125, 0, 0.5625, 1, 0.25, 0.75),
                Shapes.box(0, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0, 0.25, 0.0625, 0.875, 0.9375, 0.1875)
        ));
        basic_left = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 1, 0.1875),
                Shapes.box(0, 0, 0.5625, 0.1875, 0.25, 0.75),
                Shapes.box(0, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0.125, 0.25, 0.0625, 1, 0.9375, 0.1875)
        ));
        
        
        /// refined shapes
        refined_single = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 1, 0.1875),
                Shapes.box(0.875, 0, 0, 1, 1, 0.1875),
                Shapes.box(0, 0, 0.5625, 0.1875, 0.25, 0.75),
                Shapes.box(0.8125, 0, 0.5625, 1, 0.25, 0.75),
                Shapes.box(0.125, 0.25, 0.1875, 0.875, 0.375, 0.75),
                Shapes.box(0.125, 0.375, 0.0625, 0.875, 0.9375, 0.1875),
                Shapes.box(0, 0.25, 0.1875, 0.125, 0.5625, 0.75),
                Shapes.box(0.875, 0.25, 0.1875, 1, 0.5625, 0.75),
                Shapes.box(0.125, 0.25, 0, 0.875, 0.375, 0.1875)
        )));
        refined_middle = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0.4375, 0, -0.00625, 0.5625, 0.625, 0.11875),
                Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.25, 0.5625),
                Shapes.box(0, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0, 0.375, 0.0625, 1, 0.9375, 0.1875),
                Shapes.box(0, 0.25, 0, 1, 0.375, 0.1875)
        )));
        refined_right = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0.8125, 0, 0, 0.9375, 1, 0.1875),
                Shapes.box(0.75, 0, 0.5625, 0.9375, 0.25, 0.75),
                Shapes.box(0, 0.25, 0.1875, 0.8125, 0.375, 0.75),
                Shapes.box(0, 0.375, 0.0625, 0.8125, 0.9375, 0.1875),
                Shapes.box(0.8125, 0.25, 0.1875, 0.9375, 0.5625, 0.75),
                Shapes.box(0, 0.25, 0, 0.8125, 0.375, 0.1875)
        )));
        refined_left = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0.0625, 0, 0, 0.1875, 1, 0.1875),
                Shapes.box(0.0625, 0, 0.5625, 0.25, 0.25, 0.75),
                Shapes.box(0.1875, 0.25, 0.1875, 1, 0.375, 0.75),
                Shapes.box(0.1875, 0.375, 0.0625, 1, 0.9375, 0.1875),
                Shapes.box(0.0625, 0.25, 0.1875, 0.1875, 0.5625, 0.75),
                Shapes.box(0.1875, 0.25, 0, 1, 0.375, 0.1875)
        )));
        
        
        /// luxury shapes
        luxury_single = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 1, 0.125),
                Shapes.box(0.875, 0, 0, 1, 1, 0.125),
                Shapes.box(0.09375, 0.25, 0.125, 0.90625, 0.375, 0.6875),
                Shapes.box(0.125, 0.375, 0.0375, 0.875, 0.8625, 0.1),
                Shapes.box(0, 0.25, 0.125, 0.09375, 0.5625, 0.625),
                Shapes.box(0.90625, 0.25, 0.125, 1, 0.5625, 0.625),
                Shapes.box(0.125, 0.25, 0, 0.875, 0.375, 0.125),
                Shapes.box(0.125, 0.8125, 0, 0.875, 0.9375, 0.0625),
                Shapes.box(0.125, 0.25, 0.6875, 0.875, 0.375, 0.75),
                Shapes.box(0, 0, 0.625, 0.125, 0.625, 0.75),
                Shapes.box(0.875, 0, 0.625, 1, 0.625, 0.75)
        )));
        luxury_middle = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0.375, 0, 0.0625, 0.625, 0.25, 0.125),
                Shapes.box(0, 0.25, 0.125, 1, 0.375, 0.6875),
                Shapes.box(0, 0.375, 0.0375, 1, 0.8625, 0.1),
                Shapes.box(0, 0.25, 0, 1, 0.375, 0.125),
                Shapes.box(0, 0.8125, 0, 1, 0.9375, 0.0625),
                Shapes.box(0, 0.25, 0.6875, 1, 0.375, 0.75),
                Shapes.box(0.4375, 0, 0.375, 0.5625, 0.25, 0.5)
        )));
        luxury_right = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0.875, 0, 0, 1, 1, 0.125),
                Shapes.box(0, 0.25, 0.125, 0.90625, 0.375, 0.6875),
                Shapes.box(0, 0.375, 0.0375, 0.875, 0.8625, 0.1),
                Shapes.box(0.90625, 0.25, 0.125, 1, 0.5625, 0.625),
                Shapes.box(0.125, 0.25, 0, 0.875, 0.375, 0.125),
                Shapes.box(0, 0.8125, 0, 0.875, 0.9375, 0.0625),
                Shapes.box(0, 0.25, 0.6875, 0.875, 0.375, 0.75),
                Shapes.box(0.875, 0, 0.625, 1, 0.625, 0.75)
        )));
        luxury_left = Shapes.rotateHorizontal(Shapes.or(Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 1, 0.125),
                Shapes.box(0.09375, 0.25, 0.125, 1, 0.375, 0.6875),
                Shapes.box(0.125, 0.375, 0.0375, 1, 0.8625, 0.1),
                Shapes.box(0, 0.25, 0.125, 0.09375, 0.5625, 0.625),
                Shapes.box(0.125, 0.25, 0, 1, 0.375, 0.125),
                Shapes.box(0.125, 0.8125, 0, 1, 0.9375, 0.0625),
                Shapes.box(0.125, 0.25, 0.6875, 1, 0.375, 0.75),
                Shapes.box(0, 0, 0.625, 0.125, 0.625, 0.75)
        )));

    }

}
