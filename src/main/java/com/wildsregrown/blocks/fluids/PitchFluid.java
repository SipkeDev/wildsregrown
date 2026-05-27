package com.wildsregrown.blocks.fluids;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModFluids;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class PitchFluid extends FlowingFluid {

    public PitchFluid(){
        registerDefaultState(defaultFluidState().setValue(BlockStateProperties.LIT, true));
    }

    public Fluid getFlowing() {
        return ModFluids.PITCH_FLOWING;
    }

    public Fluid getSource() {
        return ModFluids.PITCH;
    }

    public Item getBucket() {
        return Items.MILK_BUCKET;
    }

    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        if (!state.isSource() && !(Boolean)state.getValue(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.LAVA_AMBIENT, SoundSource.AMBIENT, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        }else{
            float x = pos.getX() + random.nextFloat();
            float y = pos.getY() + random.nextFloat();
            float z = pos.getZ() + random.nextFloat();
            int rand = random.nextInt(10);
            if (state.getValue(BlockStateProperties.LIT) ? rand > 8 : rand == 0) {
                world.addParticle(ParticleTypes.LAVA, x, y, z, random.nextDouble()-0.5, random.nextDouble()-0.5,random.nextDouble()-0.5);
            }
        }

    }

    public @Nullable ParticleOptions getDripParticle() {
        return ParticleTypes.ASH;
    }

    protected boolean canConvertToSource(ServerLevel world) {
        return false;
    }

    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    protected void entityInside(Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
        if (world.getFluidState(pos).getValueOrElse(BlockStateProperties.LIT, false)){
            handler.apply(InsideBlockEffectType.FIRE_IGNITE);
        }
    }

    public int getSlopeFindDistance(LevelReader world) {
        return 2;
    }

    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.pitch.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.PITCH || fluid == ModFluids.PITCH_FLOWING;
    }

    public int getDropOff(LevelReader world) {
        return 2;
    }

    public int getTickDelay(LevelReader world) {
        return 5;
    }

    public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Still extends PitchFluid {
        public Still() {}

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(BlockStateProperties.LIT);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends PitchFluid {

        public Flowing() {}

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL, BlockStateProperties.LIT);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

}
