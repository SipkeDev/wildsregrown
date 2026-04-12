package com.wildsregrown.blocks.fluids;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class LayeredFluidBlock extends FluidBlock{

    public static final IntProperty level = Properties.LEVEL_1_8;
    private static final BooleanProperty flowing = Properties.WATERLOGGED;
    private final List<FluidState> statesByLevel;

    public LayeredFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
        this.statesByLevel = Lists.newArrayList();
        for(int i : level.getValues()) {
            this.statesByLevel.add(fluid.getStill(false));//.with(FlowableFluid.LEVEL, i));
        }
        for(int i : level.getValues()) {
            this.statesByLevel.add(fluid.getFlowing(i,false));
        }
        this.statesByLevel.add(fluid.getFlowing(8, true));
        this.setDefaultState(this.getStateManager().getDefaultState().with(LEVEL, 8));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.shouldTreatFluidAsCube()) {
            return VoxelShapes.fullCube();
        } else {
            return context.isAbove(COLLISION_SHAPE, pos, true) && state.get(LEVEL) == 0 && context.canWalkOnFluid(world.getFluidState(pos.up()), state.getFluidState()) ? COLLISION_SHAPE : VoxelShapes.empty();
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL, flowing);
    }

    public ItemStack tryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state) {
        if (state.getFluidState().isStill() && state.get(LEVEL) > 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            return new ItemStack(this.fluid.getBucketItem());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.getFluidState(pos).isEqualAndStill(fluid)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.getFluidState().isStill() || neighborState.getFluidState().isStill()) {
            tickView.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
        return state;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        int i = state.get(LEVEL);
        if (state.get(flowing)){i += 8;}
        return this.statesByLevel.get(Math.min(i, statesByLevel.size()-1));
    }

}
