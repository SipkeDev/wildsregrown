package com.wildsregrown.data.tags;

import com.wildsregrown.registries.ModFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import java.util.concurrent.CompletableFuture;

public class FluidTagProvider extends FabricTagProvider.FluidTagProvider {

    public FluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void addTags(HolderLookup.Provider registrywrapper) {

        TagAppender<Fluid, Fluid> water = valueLookupBuilder(FluidTags.WATER);
        water.add(ModFluids.PITCH);
        water.add(ModFluids.PITCH_FLOWING);

    }

}