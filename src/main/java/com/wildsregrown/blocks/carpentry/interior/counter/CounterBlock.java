package com.wildsregrown.blocks.carpentry.interior.counter;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
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
import wildsregrown.api.block.abstracts.CornerConnectingBlock;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.item.MultiSelectableBlock;

public class CounterBlock extends CornerConnectingBlock implements ITintedBlock, MultiSelectableBlock {

    private static final VoxelShape[] straight;
    private static final VoxelShape[] inner_left;
    private static final VoxelShape[] inner_right;
    private static final VoxelShape[] outer_left;
    private static final VoxelShape[] outer_right;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final Identifier id;

    public CounterBlock(Identifier id, Properties settings){
        super(settings);
        this.id = id;
        //this.registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE).setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT));
    }

    @Override
    public Item asItem() {
        return asItem(id);
    }

    @Override
    protected boolean canConnectToBlock(BlockState blockState) {
        Block block = blockState.getBlock();
        return block instanceof CounterBlock || block instanceof CounterShelvesBlock || block instanceof CounterChestBlock;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, BlockStateProperties.STAIRS_SHAPE, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)){
            case NORTH -> {
                return getShape(state, 0);
            }
            case EAST -> {
                return getShape(state, 1);
            }
            case SOUTH -> {
                return getShape(state, 2);
            }
            case WEST -> {
                return getShape(state, 3);
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    private static VoxelShape getShape(BlockState state, int i){
        switch (state.getValue(BlockStateProperties.STAIRS_SHAPE)){
            case STRAIGHT -> {
                return straight[i];
            }
            case INNER_LEFT -> {
                return inner_left[i];
            }
            case INNER_RIGHT -> {
                return inner_right[i];
            }
            case OUTER_LEFT -> {
                return outer_left[i];
            }
            case OUTER_RIGHT -> {
                return outer_right[i];
            }
        }
        return Shapes.block();
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
        straight = new VoxelShape[4];
        straight[0] = VoxelTransform.mirrorX(Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0, 0, 1, 0.875, 0.75)
        ));
        straight[1] = VoxelTransform.rotate90(straight[0]);
        straight[2] = VoxelTransform.rotate180(straight[0]);
        straight[3] = VoxelTransform.rotate270(straight[0]);

        inner_right = new VoxelShape[4];
        inner_right[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0, 0, 1, 0.875, 0.75),
                Shapes.box(0.25, 0, 0.75, 1, 0.875, 1)
        );
        inner_right[1] = VoxelTransform.rotate90(inner_right[0]);
        inner_right[2] = VoxelTransform.rotate180(inner_right[0]);
        inner_right[3] = VoxelTransform.rotate270(inner_right[0]);

        inner_left = new VoxelShape[4];
        inner_left[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0, 0, 1, 0.875, 0.75),
                Shapes.box(0, 0, 0.75, 0.75, 0.875, 1)
        );
        inner_left[1] = VoxelTransform.rotate90(inner_left[0]);
        inner_left[2] = VoxelTransform.rotate180(inner_left[0]);
        inner_left[3] = VoxelTransform.rotate270(inner_left[0]);

        outer_right = new VoxelShape[4];
        outer_right[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.25, 0, 0, 1, 0.875, 0.75)
        );
        outer_right[1] = VoxelTransform.rotate90(outer_right[0]);
        outer_right[2] = VoxelTransform.rotate180(outer_right[0]);
        outer_right[3] = VoxelTransform.rotate270(outer_right[0]);

        outer_left = new VoxelShape[4];
        outer_left[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0, 0, 0, 0.75, 0.875, 0.75)
        );
        outer_left[1] = VoxelTransform.rotate90(outer_left[0]);
        outer_left[2] = VoxelTransform.rotate180(outer_left[0]);
        outer_left[3] = VoxelTransform.rotate270(outer_left[0]);
    }
}
