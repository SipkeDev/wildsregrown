package com.wildsregrown.blocks.wood.utensils;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class Bowl extends Block implements ITintedBlock {

    private static final VoxelShape shape;

    public Bowl(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ModProperties.LINSEED_PAINT, LinSeedPaintable.NONE));
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return hasTopRim(world, blockPos) || sideCoversSmallSquare(world, blockPos, Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ModProperties.LINSEED_PAINT);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    static {
        shape = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
                VoxelShapes.cuboid(0.6875, 0, 0.3125, 0.75, 0.125, 0.6875),
                VoxelShapes.cuboid(0.25, 0, 0.3125, 0.3125, 0.125, 0.6875),
                VoxelShapes.cuboid(0.25, 0, 0.6875, 0.75, 0.125, 0.75),
                VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.125, 0.3125),
                VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 0.1875, 0.25),
                VoxelShapes.cuboid(0.1875, 0.0625, 0.75, 0.8125, 0.1875, 0.8125),
                VoxelShapes.cuboid(0.75, 0.0625, 0.25, 0.8125, 0.1875, 0.75),
                VoxelShapes.cuboid(0.1875, 0.0625, 0.25, 0.25, 0.1875, 0.75),
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.1875, 0.25, 0.875),
                VoxelShapes.cuboid(0.8125, 0.125, 0.125, 0.875, 0.25, 0.875),
                VoxelShapes.cuboid(0.1875, 0.125, 0.8125, 0.8125, 0.25, 0.875),
                VoxelShapes.cuboid(0.1875, 0.125, 0.125, 0.8125, 0.25, 0.1875)
        );
    }

}

