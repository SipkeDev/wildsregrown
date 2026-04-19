package com.wildsregrown.blocks.fluids;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModFluids;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class SweetWaterFluidBlock extends Block implements FluidDrainable{

    public static final IntProperty level = FlowableFluid.LEVEL;
    public static final IntProperty vector = ModProperties.vector;
    public static final IntProperty velocity = ModProperties.velocity;
    protected final Fluid fluid;

    public SweetWaterFluidBlock(Settings settings) {
        super(settings);
        this.fluid = ModFluids.SWEET_WATER;
        this.setDefaultState(this.getStateManager().getDefaultState().with(level, 8));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public MapCodec<SweetWaterFluidBlock> getCodec() {
        return createCodec(SweetWaterFluidBlock::new);
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0, 0.05, 0.05, 0.05);
    }

    protected boolean hasRandomTicks(BlockState state) {
        return state.getFluidState().hasRandomTicks();
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        state.getFluidState().onRandomTick(world, pos, random);
    }

    protected boolean isTransparent(BlockState state) {
        return false;
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return !this.fluid.isIn(FluidTags.LAVA);
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getFluidState().getFluid().matchesType(this.fluid);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(level, vector, velocity);
    }

    @Override
    public ItemStack tryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.empty();
    }

    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.getFluidState().isStill() || neighborState.getFluidState().isStill()) {
            tickView.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        if (state.isOf(this)) {
            WildsRegrown.LOGGER.info("fluid state getter: " + state);
            return this.fluid.getDefaultState()
                    .with(level, state.get(level))
                    .with(vector, state.get(vector))
                    .with(velocity, state.get(velocity))
                    ;
        }else {
            return this.fluid.getDefaultState();
        }
    }

}
