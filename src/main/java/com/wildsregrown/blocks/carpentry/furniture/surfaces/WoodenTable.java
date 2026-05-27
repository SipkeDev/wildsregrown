package com.wildsregrown.blocks.carpentry.furniture.surfaces;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.abstracts.HorizontalConnectingBlock;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenTable extends HorizontalConnectingBlock implements ITintedBlock {

    private static final Map<Direction, VoxelShape> basic_single;
    private static final Map<Direction, VoxelShape> basic_middle;
    private static final Map<Direction, VoxelShape> basic_left;
    private static final Map<Direction, VoxelShape> basic_right;

    private static final Map<Direction, VoxelShape> refined_single;
    private static final Map<Direction, VoxelShape> refined_middle;
    private static final Map<Direction, VoxelShape> refined_left;
    private static final Map<Direction, VoxelShape> refined_right;

    private static final EnumProperty<HorizontalConnected> SHAPE = WRGProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final int tier;

    public WoodenTable(Properties settings, int tier){
        super(settings);
        this.tier = tier;
        //this.registerDefaultState(defaultBlockState().setValue(SHAPE, HorizontalConnected.SINGLE).setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public boolean isConnectingBlock(BlockState state, BlockState current) {
        if(state.getBlock() instanceof WoodenTable table){
            return tier == table.tier;
        }else {
            return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, SHAPE, FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction dir = state.getValue(FACING);
        return switch (state.getValue(SHAPE)){
            case SINGLE -> switch (tier){
                case 1 -> refined_single.get(dir);
                case 2 -> Shapes.block();
                default -> basic_single.get(dir);
            };
            case MIDDLE -> switch (tier){
                case 1 -> refined_middle.get(dir);
                case 2 -> Shapes.block();
                default -> basic_middle.get(dir);
            };
            case LEFT -> switch (tier){
                case 1 -> refined_left.get(dir);
                case 2 -> Shapes.block();
                default -> basic_left.get(dir);
            };
            case RIGHT -> switch (tier){
                case 1 -> refined_right.get(dir);
                case 2 -> Shapes.block();
                default -> basic_right.get(dir);
            };
        };
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

        basic_single = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.75, 0, 1, 1, 1),
                Shapes.box(0.0625, 0, 0.0625, 0.25, 0.75, 0.25),
                Shapes.box(0.75, 0, 0.0625, 0.9375, 0.75, 0.25),
                Shapes.box(0.0625, 0, 0.75, 0.25, 0.75, 0.9375),
                Shapes.box(0.75, 0, 0.75, 0.9375, 0.75, 0.9375)
        ));

        basic_middle = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.75, 0, 1, 1, 1)
        ));

        basic_left = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.75, 0, 1, 1, 1),
                Shapes.box(0.0625, 0, 0.0625, 0.25, 0.75, 0.25),
                Shapes.box(0.0625, 0, 0.75, 0.25, 0.75, 0.9375)
        ));

        basic_right = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.75, 0, 1, 1, 1),
                Shapes.box(0.75, 0, 0.0625, 0.9375, 0.75, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.9375, 0.75, 0.9375)
        ));

        refined_single = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0.375, 0.46875, 1, 0.5, 0.53125),
                Shapes.box(0.0625, 0.8125, 0.1875, 0.1875, 0.875, 0.8125),
                Shapes.box(0.0625, 0.6875, 0.25, 0.1875, 0.8125, 0.75),
                Shapes.box(0.0625, 0.25, 0.34375, 0.1875, 0.6875, 0.65625),
                Shapes.box(0.0625, 0.0625, 0.125, 0.1875, 0.125, 0.875),
                Shapes.box(0.0625, 0.125, 0.25, 0.1875, 0.25, 0.75),
                Shapes.box(0.03125, 0, 0.0625, 0.21875, 0.0625, 0.375),
                Shapes.box(0.03125, 0, 0.625, 0.21875, 0.0625, 0.9375),
                Shapes.box(0.8125, 0.8125, 0.1875, 0.9375, 0.875, 0.8125),
                Shapes.box(0.8125, 0.6875, 0.25, 0.9375, 0.8125, 0.75),
                Shapes.box(0.8125, 0.25, 0.34375, 0.9375, 0.6875, 0.65625),
                Shapes.box(0.8125, 0.0625, 0.125, 0.9375, 0.125, 0.875),
                Shapes.box(0.8125, 0.125, 0.25, 0.9375, 0.25, 0.75),
                Shapes.box(0.78125, 0, 0.0625, 0.96875, 0.0625, 0.375),
                Shapes.box(0.78125, 0, 0.625, 0.96875, 0.0625, 0.9375)
        ));

        refined_middle = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0.375, 0.46875, 1, 0.5, 0.53125)
        ));

        refined_left = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.0625, 0.375, 0.46875, 1, 0.5, 0.53125),
                Shapes.box(0.1875, 0.8125, 0.1875, 0.3125, 0.875, 0.8125),
                Shapes.box(0.1875, 0.6875, 0.25, 0.3125, 0.8125, 0.75),
                Shapes.box(0.1875, 0.25, 0.34375, 0.3125, 0.6875, 0.65625),
                Shapes.box(0.1875, 0.0625, 0.125, 0.3125, 0.125, 0.875),
                Shapes.box(0.1875, 0.125, 0.25, 0.3125, 0.25, 0.75),
                Shapes.box(0.15625, 0, 0.0625, 0.34375, 0.0625, 0.375),
                Shapes.box(0.15625, 0, 0.625, 0.34375, 0.0625, 0.9375)
        ));

        refined_right = Shapes.rotateHorizontal(VoxelTransform.mirrorX(refined_left.get(Direction.NORTH)));

    }

}
