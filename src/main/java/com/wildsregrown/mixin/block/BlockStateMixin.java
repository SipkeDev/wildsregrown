package com.wildsregrown.mixin.block;

import com.wildsregrown.blocks.fluids.SetAbleFluidState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateMixin implements SetAbleFluidState {

    @Shadow
    private FluidState fluidState;

    @Override
    public void wrg$setFluidState(FluidState fluidState) {
        this.fluidState = fluidState;
    }

}
