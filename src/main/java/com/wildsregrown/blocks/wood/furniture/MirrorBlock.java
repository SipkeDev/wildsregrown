package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class MirrorBlock extends Block {

    private static VoxelShape[] SHAPES;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public MirrorBlock(Settings settings){
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {
                return SHAPES[0];
            }
            case EAST -> {
                return SHAPES[1];
            }
            case SOUTH -> {
                return SHAPES[2];
            }
            case WEST -> {
                return SHAPES[3];
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    static {
        SHAPES = new VoxelShape[4];
        SHAPES[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0.0625, 0, 0.6875, 0.8875, 0.125),
                VoxelShapes.cuboid(0.25, 0.0625, 0, 0.3125, 0.9375, 0.1875),
                VoxelShapes.cuboid(0.6875, 0.0625, 0, 0.75, 0.9375, 0.1875),
                VoxelShapes.cuboid(0, 0, 0, 1, 0.0625, 0.1875),
                VoxelShapes.cuboid(0.3125, 0.875, 0, 0.6875, 0.9375, 0.1875),
                VoxelShapes.cuboid(0.0625, 0.625, 0, 0.25, 0.6875, 0.125),
                VoxelShapes.cuboid(0.75, 0.625, 0, 0.9375, 0.6875, 0.125),
                VoxelShapes.cuboid(0, 0.0625, 0, 0.0625, 0.6875, 0.125),
                VoxelShapes.cuboid(0.9375, 0.0625, 0, 1, 0.6875, 0.125),
                VoxelShapes.cuboid(0.0625, 0.0625, 0, 0.25, 0.625, 0.0625),
                VoxelShapes.cuboid(0.75, 0.0625, 0, 0.9375, 0.625, 0.0625)
        );
        SHAPES[1] = VoxelTransform.rotate90(SHAPES[0]);
        SHAPES[2] = VoxelTransform.rotate180(SHAPES[0]);
        SHAPES[3] = VoxelTransform.rotate270(SHAPES[0]);
    }

}
