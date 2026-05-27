package com.wildsregrown.blocks.stonemasonry.castle;

import com.wildsregrown.blocks.abstracts.AbstractArrowSlitBlock;
import com.wildsregrown.blocks.properties.ModProperties;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class StoneArrowSlitBlock extends AbstractArrowSlitBlock {

    private static final Map<Direction, VoxelShape> basic_single;
    private static final Map<Direction, VoxelShape> basic_top;
    private static final Map<Direction, VoxelShape> basic_middle;
    private static final Map<Direction, VoxelShape> basic_bottom;
    private static final Map<Direction, VoxelShape> basic_floor;

    private static final Map<Direction, VoxelShape> refined_single;
    private static final Map<Direction, VoxelShape> refined_top;
    private static final Map<Direction, VoxelShape> refined_middle;
    private static final Map<Direction, VoxelShape> refined_bottom;
    private static final Map<Direction, VoxelShape> refined_floor;

    private static final Map<Direction, VoxelShape> cross_single;
    private static final Map<Direction, VoxelShape> cross_top;
    private static final Map<Direction, VoxelShape> cross_middle;
    private static final Map<Direction, VoxelShape> cross_bottom;
    private static final Map<Direction, VoxelShape> cross_floor;

    private final int tier;

    public StoneArrowSlitBlock(Properties settings, int tier) {
        super(settings);
        this.tier = tier;
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return switch (state.getValue(ModProperties.ARROW_SLIT_CONNECTED)) {
            case TOP -> switch (tier){
                case 1 -> refined_top.get(dir);
                case 2 -> cross_top.get(dir);
                default -> basic_top.get(dir);
            };
            case MIDDLE -> switch (tier){
                case 1 -> refined_middle.get(dir);
                case 2 -> cross_middle.get(dir);
                default -> basic_middle.get(dir);
            };
            case BOTTOM -> switch (tier){
                case 1 -> refined_bottom.get(dir);
                case 2 -> cross_bottom.get(dir);
                default -> basic_bottom.get(dir);
            };
            case BOTTOM_FLOOR -> switch (tier){
                case 1 -> refined_floor.get(dir);
                case 2 -> cross_floor.get(dir);
                default -> basic_floor.get(dir);
            };
            default -> switch (tier){
                case 1 -> refined_single.get(dir);
                case 2 -> cross_single.get(dir);
                default -> basic_single.get(dir);
            };
        };
    }

    static {
        basic_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.1875, 0.875, 0.4375, 0.8125, 1, 0.89375),
                Shapes.box(0.375, 0.690625, 0.89375, 0.625, 1, 1),
                Shapes.box(0.375, 0, 0.89375, 0.625, 0.30625, 1),
                Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.125, 0.89375),
                Shapes.box(0, 0, 0.4375, 0.1875, 1, 0.89375),
                Shapes.box(0.8125, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.625, 0, 0.89375, 1, 1, 1),
                Shapes.box(0, 0, 0.89375, 0.375, 1, 1)
        )));
        basic_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.1875, 0.875, 0.4375, 0.8125, 1, 0.89375),
                Shapes.box(0.375, 0.625, 0.89375, 0.625, 1, 1),
                Shapes.box(0, 0, 0.4375, 0.1875, 1, 0.89375),
                Shapes.box(0.8125, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.625, 0, 0.89375, 1, 1, 1),
                Shapes.box(0, 0, 0.89375, 0.375, 1, 1)
        )));
        basic_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0.8125, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.625, 0, 0.89375, 1, 1, 1),
                Shapes.box(0, 0, 0.89375, 0.375, 1, 1),
                Shapes.box(0, 0, 0.4375, 0.1875, 1, 0.89375)
        )));
        basic_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(basic_top.get(Direction.NORTH)));
        basic_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0.125, 0.875, 0.375, 1, 1),
                Shapes.box(0, 0.125, 0.125, 0.186125, 1, 0.875),
                Shapes.box(0.813875, 0.125, 0.125, 1, 1, 0.875),
                Shapes.box(0.625, 0.125, 0.875, 1, 1, 1),
                Shapes.box(0, 0, 0, 1, 1, 0.125),
                Shapes.box(0.15625, 0.45625, 1.2875, 0.84375, 0.51875, 1.525),
                Shapes.box(0, 0, 0.125, 1, 0.125, 1)
        )));
        /// refined
        refined_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.89375),
                Shapes.box(0, 0, 0.89375, 0.3125, 1, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.6875, 0, 0.89375, 1, 1, 1),
                Shapes.box(0.190625, 0, 0.43875, 0.809375, 0.063375, 0.89375),
                Shapes.box(0.3125, 0, 0.89375, 0.6875, 0.14375, 1),
                Shapes.box(0.1875, 0.06325, 0.45, 0.8125, 0.06325, 0.9125),
                Shapes.box(0.3125, 0.85625, 0.89375, 0.6875, 1, 1),
                Shapes.box(0.1875, 0.93675, 0.45, 0.8125, 0.93675, 0.9125),
                Shapes.box(0.190625, 0.936625, 0.43875, 0.809375, 1, 0.89375)
        )));
        refined_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.89375),
                Shapes.box(0, 0, 0.89375, 0.3125, 1, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.6875, 0, 0.89375, 1, 1, 1),
                Shapes.box(0.3125, 0.85625, 0.89375, 0.6875, 1, 1),
                Shapes.box(0.190625, 0.936625, 0.43875, 0.809375, 1, 0.89375)
        )));
        refined_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                        Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.89375),
                        Shapes.box(0, 0, 0.89375, 0.3125, 1, 1),
                        Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.89375),
                        Shapes.box(0.6875, 0, 0.89375, 1, 1, 1)
        )));
        refined_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(refined_top.get(Direction.NORTH)));
        refined_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 0.190625, 1, 0.89375),
                Shapes.box(0, 0, 0.89375, 0.3125, 1, 1),
                Shapes.box(0.809375, 0, 0, 1, 1, 0.89375),
                Shapes.box(0.6875, 0, 0.89375, 1, 1, 1),
                Shapes.box(0.190625, 0, 0, 0.809375, 1, 0.4375),
                Shapes.box(0.190625, 0, 0.4375, 0.809375, 0, 0.89375),
                Shapes.box(0.3125, 0, 0.89375, 0.6875, 0.125, 1)
        )));
        /// cross
        cross_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.89375),
                Shapes.box(0, 0, 0.89375, 0.375, 0.5, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0, 0.625, 0.89375, 0.375, 1, 1),
                Shapes.box(0.625, 0.625, 0.89375, 1, 1, 1),
                Shapes.box(0.625, 0, 0.89375, 1, 0.5, 1)
        )));
        cross_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.89375),
                Shapes.box(0.0553125, 0.000125, 0.351875, 0.2428125, 0.999875, 0.82375),
                Shapes.box(0, 0, 0.89375, 0.3125, 1, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.89375),
                Shapes.box(0.6875, 0, 0.89375, 1, 1, 1),
                Shapes.box(0.7571875, 0.000125, 0.351875, 0.9446875, 0.999875, 0.82375),
                Shapes.box(0.3125, 0.48125, 0.89375, 0.6875, 1, 1),
                Shapes.box(0.190625, 0.561625, 0.43875, 0.809375, 1, 0.89375)
        )));
        cross_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(Shapes.or(
                        Shapes.box(0, 0, 0.4375, 0.190625, 0.75, 0.89375),
                        Shapes.box(0, 0, 0.89375, 0.3125, 0.75, 1),
                        Shapes.box(0.809375, 0, 0.4375, 1, 0.75, 0.89375),
                        Shapes.box(0.6875, 0, 0.89375, 1, 0.75, 1)
        ))));
        cross_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(cross_top.get(Direction.NORTH)));
        cross_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 1, 1, 0.4375),
                Shapes.box(0, 0, 0.4375, 0.1875, 1, 1),
                Shapes.box(0.8125, 0, 0.4375, 1, 1, 1),
                Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.26875, 1)
        )));
    }
}
