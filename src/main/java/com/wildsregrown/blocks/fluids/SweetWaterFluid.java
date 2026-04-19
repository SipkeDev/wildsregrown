package com.wildsregrown.blocks.fluids;

import com.sipke.api.rivers.RiverConstants;
import com.sipke.core.pos.INeighbours;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.ModProperties;
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
import org.jspecify.annotations.Nullable;

import java.util.Optional;

import static com.wildsregrown.blocks.properties.ModProperties.*;

public abstract class SweetWaterFluid extends FlowableFluid implements INeighbours {

    public SweetWaterFluid(){
        this.setDefaultState(this.getDefaultState()
                .with(FALLING, false)
                .with(LEVEL, 1)
                .with(vector, 0)
                .with(velocity, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        builder.add(FALLING, LEVEL, vector, velocity);
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

            if (rand == 0) {
                world.addParticleClient(ParticleTypes.UNDERWATER, x, y, z, 0.0, 0.0, 0.0);
            } else if (rand > 17) {
                if (world.isAir(pos.up())) {
                    int vec = state.get(vector);
                    double v = RiverConstants.velocity[state.get(velocity)];
                    world.addParticleClient(ParticleTypes.WHITE_SMOKE, x, y, z,
                            RiverConstants.vec2d[vec].x*v,
                            0.05 + (0.15*v),
                            RiverConstants.vec2d[vec].y*v
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
        return false;
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
            int vec = state.get(vector);
            double v = RiverConstants.velocity[state.get(velocity)];
            entity.move(MovementType.SELF, new Vec3d(
                            0.12 * v * RiverConstants.vec2d[vec].x,
                            0.003,
                            0.12 * v * RiverConstants.vec2d[vec].y
                    )
            );
            //WildsRegrown.LOGGER.info("vec mov: " + 0.05 * RiverConstants.vectors[vecX]);
        }
    }

    @Override
    public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {

        if (matchesType(state.getFluid())) {
            int vec = state.get(vector);
            double vx = RiverConstants.vec2d[vec].x;
            double vz = RiverConstants.vec2d[vec].y;
            double v = RiverConstants.velocity[state.get(velocity)];
            Vec3d vec3d = new Vec3d(vx*v, 0, vz*v);
            vec3d.multiply(6.0);
            if (state.get(FALLING)) {
                vec3d = vec3d.normalize().add(0, -6.0, 0);
            }
            return vec3d.normalize();
        }

        return new Vec3d(0,0,0).normalize();
    }

    @Override
    public void onScheduledTick(ServerWorld world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        super.onScheduledTick(world, pos, blockState, fluidState);
        WildsRegrown.LOGGER.info("Ticked: " + world.getFluidState(pos));
    }

    @Override
    protected boolean hasRandomTicks() {return false;}

    @Override
    protected void onRandomTick(ServerWorld world, BlockPos pos, FluidState state, Random random) {
        WildsRegrown.LOGGER.info("Random fluid tick from block");
    }

    /**
     * Update methodes
     * @return
     */
    @Override
    protected FluidState getUpdatedState(ServerWorld world, BlockPos pos, BlockState state) {
        FluidState fluidState = super.getUpdatedState(world, pos, state);
        if (state.isOf(ModBlocks.sweet_water)){
            if (state.contains(vector)) {
                fluidState = fluidState
                        .with(LEVEL, state.get(LEVEL))
                        .with(vector, state.get(vector))
                        .with(velocity, state.get(velocity));
            }
        }
        return fluidState;
    }

    public int getMaxFlowDistance(WorldView world) {
        return 4;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState) {
        BlockState blockState = ModBlocks.sweet_water.getDefaultState()
                .with(LEVEL, fluidState.get(LEVEL))
                .with(vector, fluidState.get(vector))
                .with(velocity, fluidState.get(velocity))
                ;
        //((SetAbleFluidState)blockState).wrg$setFluidState(fluidState);
        return blockState;
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
        return 12;
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
