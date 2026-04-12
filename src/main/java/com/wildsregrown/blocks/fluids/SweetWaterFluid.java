package com.wildsregrown.blocks.fluids;

import com.sipke.core.pos.INeighbours;
import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModFluids;
import com.wildsregrown.registries.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.CollisionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.MovementType;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.rule.GameRules;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

import static com.wildsregrown.blocks.properties.ModProperties.*;

public abstract class SweetWaterFluid extends FlowableFluid implements INeighbours {

    private static final double[] vector;
    static {
        vector = new double[vecX.getValues().size()];
        for (int i : vecX.getValues()){
            vector[i] = MathUtil.range(i, 0,16,-1f, 1f);
        }
    }

    public SweetWaterFluid(){
        this.setDefaultState(this.getDefaultState()
                .with(FALLING, false)
                .with(LEVEL, 1)
                .with(vecX, 8)
                .with(vecZ, 8)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        builder.add(FALLING, LEVEL, vecX, vecZ);
    }

    public Fluid getFlowing() {
        return ModFluids.SWEET_WATER_FLOWING;
    }

    public Fluid getStill() {
        return ModFluids.SWEET_WATER;
    }

    public Item getBucketItem() {
        return ModItems.sweet_water_bucket;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean) state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSoundClient(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.AMBIENT, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (state.isStill()) {

            float x = pos.getX() + random.nextFloat();
            float y = pos.getY() + random.nextFloat();
            float z = pos.getZ() + random.nextFloat();
            int rand = random.nextInt(20);
            int vx = state.get(vecX);
            int vz = state.get(vecZ);

            if (rand == 0) {
                world.addParticleClient(ParticleTypes.UNDERWATER, x, y, z, 0.0, 0.0, 0.0);
            } else if (vx != 8 && vz != 8) {
                if (rand > 10 && state.isStill() && world.getBlockState(pos.up()).isAir()) {
                    world.addParticleClient(ParticleTypes.LARGE_SMOKE, x, y, z,
                            0.25 * vector[vx],
                            0.02 * Math.abs(vector[vx]*vector[vz]),
                            0.25 * vector[vz]
                    );
                }
            }
        }

    }

    @Override
    public @Nullable ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean isInfinite(ServerWorld world) {
        return world.getGameRules().getValue(GameRules.WATER_SOURCE_CONVERSION);
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected void onEntityCollision(World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        handler.addEvent(CollisionEvent.EXTINGUISH);

        FluidState state = world.getFluidState(pos);
        if (state.isEqualAndStill(this)) {
            int vx = state.get(vecX);
            int vz = state.get(vecZ);
            entity.move(MovementType.SELF, new Vec3d(
                    0.05 * vector[vx],
                    0.001,
                    0.05 * vector[vz]
                    )
            );
        }
    }

    @Override
    public void onScheduledTick(ServerWorld world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        WildsRegrown.LOGGER.info("Ticked: " + world.getFluidState(pos));
    }

    @Override
    protected FluidState getUpdatedState(ServerWorld world, BlockPos pos, BlockState state) {
        return world.getFluidState(pos);
    }

    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        WildsRegrown.LOGGER.info("Flow: " + world.getFluidState(pos));
    }

    public int getMaxFlowDistance(WorldView world) {
        return 4;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState) {
        BlockState blockState = ModBlocks.sweet_water.getDefaultState();
        ((SetAbleFluidState)blockState).wrg$setFluidState(fluidState);
        WildsRegrown.LOGGER.info("Set fluidState: " + fluidState);
        return blockState.with(FluidBlock.LEVEL, fluidState.get(LEVEL));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.SWEET_WATER || fluid == ModFluids.SWEET_WATER_FLOWING;
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 8;
    }

    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    public static class Still extends SweetWaterFluid {

        public Still() {}

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return true;
        }

    }

    public static class Flowing extends SweetWaterFluid {

        public Flowing() {}

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }

}
