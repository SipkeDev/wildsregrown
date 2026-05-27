package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.abstracts.AbstractArrowSlitBlock;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

import static com.wildsregrown.blocks.properties.connecting.ArrowSlitConnected.*;

public class WoodenArrowSlit extends AbstractArrowSlitBlock implements ITintedBlock {

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    private static final Map<Direction, VoxelShape> basic_single;
    private static final Map<Direction, VoxelShape> basic_top;
    private static final Map<Direction, VoxelShape> basic_middle;
    private static final Map<Direction, VoxelShape> basic_bottom;
    private static final Map<Direction, VoxelShape> basic_bottom_floor;

    private static final Map<Direction, VoxelShape> refined_single;
    private static final Map<Direction, VoxelShape> refined_top;
    private static final Map<Direction, VoxelShape> refined_middle;
    private static final Map<Direction, VoxelShape> refined_bottom;
    private static final Map<Direction, VoxelShape> refined_bottom_floor;

    private static final Map<Direction, VoxelShape> cross_single;
    private static final Map<Direction, VoxelShape> cross_top;
    private static final Map<Direction, VoxelShape> cross_middle;
    private static final Map<Direction, VoxelShape> cross_bottom;
    private static final Map<Direction, VoxelShape> cross_bottom_floor;

    private final int tier;

    //Defining Default BlockState
    public WoodenArrowSlit(Properties settings, int tier) {
        super(settings);
        this.tier = tier;
        registerDefaultState(defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(ModProperties.ARROW_SLIT_CONNECTED, SINGLE).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PAINT, BlockStateProperties.HORIZONTAL_FACING, ModProperties.ARROW_SLIT_CONNECTED, BlockStateProperties.WATERLOGGED);
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return switch (state.getValue(ModProperties.ARROW_SLIT_CONNECTED)){
            case SINGLE -> switch (tier) {
                case 1 -> refined_single.get(dir);
                case 2 -> cross_single.get(dir);
                default -> basic_single.get(dir);
            };
            case MIDDLE -> switch (tier){
                case 1 -> refined_middle.get(dir);
                case 2 -> cross_middle.get(dir);
                default -> basic_middle.get(dir);
            };
            case TOP -> switch (tier){
                case 1 -> refined_top.get(dir);
                case 2 -> cross_top.get(dir);
                default -> basic_top.get(dir);
            };
            case BOTTOM -> switch (tier){
                case 1 -> refined_bottom.get(dir);
                case 2 -> cross_bottom.get(dir);
                default -> basic_bottom.get(dir);
            };
            case BOTTOM_FLOOR -> switch (tier){
                case 1 -> refined_bottom_floor.get(dir);
                case 2 -> cross_bottom_floor.get(dir);
                default -> basic_bottom_floor.get(dir);
            };
            default -> Shapes.block();
        };
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        basic_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.375, 1, 1),
                Shapes.box(0.625, 0, 0.75, 1, 1, 1),
                Shapes.box(0.375, 0.8125, 0.75, 0.625, 1, 1),
                Shapes.box(0.375, 0, 0.75, 0.625, 0.1875, 1)
        )));
        basic_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.375, 1, 1),
                Shapes.box(0.625, 0, 0.75, 1, 1, 1)
        )));
        basic_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.375, 1, 1),
                Shapes.box(0.625, 0, 0.75, 1, 1, 1),
                Shapes.box(0.375, 0.6875, 0.75, 0.625, 1, 1)
        )));
        basic_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(basic_top.get(Direction.NORTH)));
        basic_bottom_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 0.375, 1, 1),
                Shapes.box(0.625, 0, 0, 1, 1, 1),
                Shapes.box(0.375, 0, 0, 0.625, 1, 0.34375)
        )));

        refined_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.3125, 1, 1),
                Shapes.box(0.6875, 0, 0.75, 1, 1, 1),
                Shapes.box(0.75, 0, 0.5625, 1, 1, 0.75),
                Shapes.box(0, 0, 0.5625, 0.25, 1, 0.75),
                Shapes.box(0.25, 0.875, 0.5625, 0.75, 1, 0.75),
                Shapes.box(0.3125, 0.875, 0.75, 0.6875, 1, 1),
                Shapes.box(0.25, 0, 0.5625, 0.75, 0.125, 0.75),
                Shapes.box(0.3125, 0, 0.75, 0.6875, 0.125, 1)
        )));
        refined_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.3125, 1, 1),
                Shapes.box(0.6875, 0, 0.75, 1, 1, 1),
                Shapes.box(0.75, 0, 0.5625, 1, 1, 0.75),
                Shapes.box(0, 0, 0.5625, 0.25, 1, 0.75)
        )));
        refined_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.75, 0.3125, 1, 1),
                Shapes.box(0.6875, 0, 0.75, 1, 1, 1),
                Shapes.box(0.75, 0, 0.5625, 1, 1, 0.75),
                Shapes.box(0, 0, 0.5625, 0.25, 1, 0.75),
                Shapes.box(0.25, 0.75, 0.5625, 0.75, 1, 0.75),
                Shapes.box(0.3125, 0.75, 0.75, 0.6875, 1, 1),
                Shapes.box(0.25, 0.75, 0.5625, 0.75, 1, 0.75),
                Shapes.box(0.3125, 0.75, 0.75, 0.6875, 1, 1)
        )));
        refined_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(refined_top.get(Direction.NORTH)));
        refined_bottom_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 0.375, 1, 0.375),
                Shapes.box(0.625, 0, 0, 1, 1, 0.375),
                Shapes.box(0.75, 0, 0.375, 1, 1, 1),
                Shapes.box(0, 0, 0.375, 0.25, 1, 1),
                Shapes.box(0.375, 0, 0, 0.625, 1, 0.125),
                Shapes.box(0.25, 0, 0.125, 0.75, 0.11875, 1)
        )));

        cross_single = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.76875),
                Shapes.box(0, 0, 0.76875, 0.375, 0.5, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.76875),
                Shapes.box(0, 0.625, 0.76875, 0.375, 1, 1),
                Shapes.box(0.625, 0.625, 0.76875, 1, 1, 1),
                Shapes.box(0.625, 0, 0.76875, 1, 0.5, 1)
        )));
        cross_middle = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 0.75, 0.76875),
                Shapes.box(0, 0, 0.76875, 0.3125, 0.75, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 0.75, 0.76875),
                Shapes.box(0.6875, 0, 0.76875, 1, 0.75, 1)
        )));
        cross_top = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.4375, 0.190625, 1, 0.76875),
                Shapes.box(0, 0, 0.76875, 0.3125, 1, 1),
                Shapes.box(0.809375, 0, 0.4375, 1, 1, 0.76875),
                Shapes.box(0.6875, 0, 0.76875, 1, 1, 1),
                Shapes.box(0.3125, 0.5, 0.76875, 0.6875, 1, 1),
                Shapes.box(0.190625, 0.5, 0.4375, 0.809375, 1, 0.76875)
        )));
        cross_bottom = Shapes.rotateHorizontal(VoxelTransform.mirrorY(cross_top.get(Direction.NORTH)));
        cross_bottom_floor = Shapes.rotateHorizontal(VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 1, 1, 0.3125),
                Shapes.box(0, 0, 0.3125, 0.1875, 1, 1),
                Shapes.box(0.8125, 0, 0.3125, 1, 1, 1),
                Shapes.box(0.1875, 0, 0.3125, 0.8125, 0.26875, 0.875)
        )));
    }

}