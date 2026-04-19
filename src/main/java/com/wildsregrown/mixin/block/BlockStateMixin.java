package com.wildsregrown.mixin.block;

import com.wildsregrown.blocks.fluids.SetAbleFluidState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class BlockStateMixin implements SetAbleFluidState {

    @Shadow
    private FluidState fluidState;

    @Override
    public void wrg$setFluidState(FluidState fluidState) {
        this.fluidState = fluidState;
    }

}
