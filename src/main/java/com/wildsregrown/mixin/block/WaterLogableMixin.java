package com.wildsregrown.mixin.block;

import com.wildsregrown.registries.ModFluids;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Waterloggable.class)
public interface WaterLogableMixin{

    /*
    @Inject(method = "Lnet/minecraft/block/Waterloggable;canFillWithFluid(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/Fluid;)Z", at = @At("HEAD"), cancellable = true)
    private void canFillWithFluid(LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(fluid == Fluids.WATER || fluid == ModFluids.SWEET_WATER);
    }

    @Inject(method = "Lnet/minecraft/block/Waterloggable;tryFillWithFluid(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/FluidState;)Z", at = @At("HEAD"), cancellable = true)
    private void tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (!(Boolean)state.get(Properties.WATERLOGGED) && (fluidState.getFluid() == Fluids.WATER || fluidState.getFluid() == ModFluids.SWEET_WATER)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, true), 3);
                world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
     */

}
