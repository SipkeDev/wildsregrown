package com.wildsregrown.registries;

import com.wildsregrown.blocks.fluids.PitchFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import static com.wildsregrown.WildsRegrown.modid;

public class ModFluids {

    public static final FlowingFluid PITCH_FLOWING = register("pitch_flowing", new PitchFluid.Flowing());
    public static final FlowingFluid PITCH = register("pitch", new PitchFluid.Still());

    public ModFluids() {}

    private static <T extends Fluid> T register(String id, T value) {
        return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(modid, id), value);
    }

}
