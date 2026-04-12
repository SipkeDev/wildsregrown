package com.wildsregrown.data;

import com.wildsregrown.registries.ModFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

class FluidTagProvider extends FabricTagProvider.FluidTagProvider {

    public FluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registrywrapper) {

        ProvidedTagBuilder<Fluid, Fluid> water = valueLookupBuilder(FluidTags.WATER);
        water.add(ModFluids.SWEET_WATER);
        water.add(ModFluids.SWEET_WATER_FLOWING);

    }

}