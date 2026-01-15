package com.wildsregrown.blocks.stone.furniture;

import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;

public class StoneTable extends Block {

    private static final VoxelShape shape = VoxelShapes.union(
            VoxelShapes.cuboid(0.6875, 0, 0.1875, 0.8125, 0.375, 0.3125),
            VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.3125, 0.375, 0.3125),
            VoxelShapes.cuboid(0.6875, 0, 0.6875, 0.8125, 0.375, 0.8125),
            VoxelShapes.cuboid(0.1875, 0, 0.6875, 0.3125, 0.375, 0.8125),
            VoxelShapes.cuboid(0.125, 0.375, 0.125, 0.875, 0.5, 0.875)
    );;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public StoneTable(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
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

}
