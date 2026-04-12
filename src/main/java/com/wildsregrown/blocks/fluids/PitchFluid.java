package com.wildsregrown.blocks.fluids;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.CollisionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public abstract class PitchFluid extends FlowableFluid {

    public PitchFluid(){
        setDefaultState(getDefaultState().with(Properties.LIT, true));
    }

    public Fluid getFlowing() {
        return ModFluids.PITCH_FLOWING;
    }

    public Fluid getStill() {
        return ModFluids.PITCH;
    }

    public Item getBucketItem() {
        return Items.MILK_BUCKET;
    }

    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSoundClient(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.AMBIENT, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        }else{
            float x = pos.getX() + random.nextFloat();
            float y = pos.getY() + random.nextFloat();
            float z = pos.getZ() + random.nextFloat();
            int rand = random.nextInt(10);
            if (state.get(Properties.LIT) ? rand > 8 : rand == 0) {
                world.addParticleClient(ParticleTypes.LAVA, x, y, z, random.nextDouble()-0.5, random.nextDouble()-0.5,random.nextDouble()-0.5);
            }
        }

    }

    public @Nullable ParticleEffect getParticle() {
        return ParticleTypes.ASH;
    }

    protected boolean isInfinite(ServerWorld world) {
        return false;
    }

    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    protected void onEntityCollision(World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (world.getFluidState(pos).get(Properties.LIT, false)){
            handler.addEvent(CollisionEvent.FIRE_IGNITE);
        }
    }

    public int getMaxFlowDistance(WorldView world) {
        return 2;
    }

    public BlockState toBlockState(FluidState state) {
        return ModBlocks.pitch.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.PITCH || fluid == ModFluids.PITCH_FLOWING;
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }

    public int getTickRate(WorldView world) {
        return 5;
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

    public static class Still extends PitchFluid {
        public Still() {}

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(Properties.LIT);
        }

        public int getLevel(FluidState state) {
            return 8;
        }

        public boolean isStill(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends PitchFluid {

        public Flowing() {}

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL, Properties.LIT);
        }

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }

}
