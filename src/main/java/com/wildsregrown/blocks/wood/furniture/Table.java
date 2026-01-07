package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class Table extends Block implements ITintedBlock {

    private static final VoxelShape shape = VoxelShapes.union(
            VoxelShapes.cuboid(0.0625, 0, 0.75, 0.25, 0.75, 0.9375),
            VoxelShapes.cuboid(0.75, 0, 0.75, 0.9375, 0.75, 0.9375),
            VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.25, 0.75, 0.25),
            VoxelShapes.cuboid(0.75, 0, 0.0625, 0.9375, 0.75, 0.25),
            VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 1)
    );
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public Table(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
