package com.wildsregrown.registries;

import com.wildsregrown.blocks.fluids.PitchFluid;
import com.wildsregrown.blocks.fluids.SweetWaterFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class ModFluids {

    public static final FlowableFluid SWEET_WATER_FLOWING = register("sweet_flowing_water", new SweetWaterFluid.Flowing());
    public static final FlowableFluid SWEET_WATER = register("sweet_water", new SweetWaterFluid.Still());

    public static final FlowableFluid PITCH_FLOWING = register("pitch_flowing", new PitchFluid.Flowing());
    public static final FlowableFluid PITCH = register("pitch", new PitchFluid.Still());

    public ModFluids() {}

    private static <T extends Fluid> T register(String id, T value) {
        return Registry.register(Registries.FLUID, Identifier.of(modid, id), value);
    }

}
