package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.CornerConnectingBlock;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CounterBlock extends CornerConnectingBlock implements ITintedBlock {

    private static final VoxelShape[] straight;
    private static final VoxelShape[] inner_left;
    private static final VoxelShape[] inner_right;
    private static final VoxelShape[] outer_left;
    private static final VoxelShape[] outer_right;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public CounterBlock(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected boolean canConnectToBlock(BlockState blockState) {
        Block block = blockState.getBlock();
        return block instanceof CounterBlock || block instanceof CounterShelvesBlock || block instanceof CounterChestBlock;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, Properties.STAIR_SHAPE, Properties.HORIZONTAL_FACING, Properties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.HORIZONTAL_FACING)){
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
                return VoxelShapes.fullCube();
            }
        }
    }

    private static VoxelShape getShape(BlockState state, int i){
        switch (state.get(Properties.STAIR_SHAPE)){
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
        return VoxelShapes.fullCube();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    static {
        straight = new VoxelShape[4];
        straight[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0, 0, 0, 1, 0.875, 0.75)
        );
        straight[1] = VoxelTransform.rotate90(straight[0]);
        straight[2] = VoxelTransform.rotate180(straight[0]);
        straight[3] = VoxelTransform.rotate270(straight[0]);

        inner_right = new VoxelShape[4];
        inner_right[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0, 0, 0, 1, 0.875, 0.75),
                VoxelShapes.cuboid(0.25, 0, 0.75, 1, 0.875, 1)
        );
        inner_right[1] = VoxelTransform.rotate90(inner_right[0]);
        inner_right[2] = VoxelTransform.rotate180(inner_right[0]);
        inner_right[3] = VoxelTransform.rotate270(inner_right[0]);

        inner_left = new VoxelShape[4];
        inner_left[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0, 0, 0, 1, 0.875, 0.75),
                VoxelShapes.cuboid(0, 0, 0.75, 0.75, 0.875, 1)
        );
        inner_left[1] = VoxelTransform.rotate90(inner_left[0]);
        inner_left[2] = VoxelTransform.rotate180(inner_left[0]);
        inner_left[3] = VoxelTransform.rotate270(inner_left[0]);

        outer_right = new VoxelShape[4];
        outer_right[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.25, 0, 0, 1, 0.875, 0.75)
        );
        outer_right[1] = VoxelTransform.rotate90(outer_right[0]);
        outer_right[2] = VoxelTransform.rotate180(outer_right[0]);
        outer_right[3] = VoxelTransform.rotate270(outer_right[0]);

        outer_left = new VoxelShape[4];
        outer_left[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0, 0, 0, 0.75, 0.875, 0.75)
        );
        outer_left[1] = VoxelTransform.rotate90(outer_left[0]);
        outer_left[2] = VoxelTransform.rotate180(outer_left[0]);
        outer_left[3] = VoxelTransform.rotate270(outer_left[0]);
    }
}
