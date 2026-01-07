package com.wildsregrown.blocks.wood.utensils;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Mug extends Bowl{

    private static final VoxelShape[] SHAPES = new VoxelShape[4];

    public Mug(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ModProperties.LINSEED_PAINT, LinSeedPaintable.NONE).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return  SHAPES[state.get(Properties.HORIZONTAL_FACING).getHorizontalQuarterTurns()];
    }

    static {
        SHAPES[1] = VoxelShapes.union(
                    VoxelShapes.cuboid(0.40625, 0, 0.40625, 0.59375, 0.0625, 0.59375),
                    VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.3125, 0.40625),
                    VoxelShapes.cuboid(0.375, 0, 0.59375, 0.625, 0.3125, 0.625),
                    VoxelShapes.cuboid(0.375, 0, 0.40625, 0.40625, 0.3125, 0.59375),
                    VoxelShapes.cuboid(0.59375, 0, 0.40625, 0.625, 0.3125, 0.59375),
                    VoxelShapes.cuboid(0.34375, 0, 0.4375, 0.375, 0.3125, 0.5625),
                    VoxelShapes.cuboid(0.25, 0.0625, 0.4375, 0.28125, 0.3125, 0.5625),
                    VoxelShapes.cuboid(0.28125, 0.21875, 0.46875, 0.34375, 0.28125, 0.53125)
        );
        SHAPES[2] = VoxelTransform.rotate90 (SHAPES[1]);
        SHAPES[3] = VoxelTransform.rotate180(SHAPES[1]);
        SHAPES[0] = VoxelTransform.rotate270(SHAPES[1]);
    }

}
