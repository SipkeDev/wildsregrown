package com.wildsregrown.mixin;

import com.wildsregrown.blocks.fluids.SetAbleFluidState;
import com.wildsregrown.world.biomes.SetableSection;
import net.minecraft.block.AbstractBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ReadableContainer;
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
