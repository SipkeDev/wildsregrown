package com.wildsregrown.registries;

import com.mojang.serialization.Codec;
import com.wildsregrown.WildsRegrown;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class ModComponents {

    //Crafting registeries
    @Deprecated
    public static final ComponentType<String> IN_WORLD_RESULT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(WildsRegrown.modid, "result"),
            ComponentType.<String>builder().codec(Codec.STRING).build());

    //Item Properties
    public static final ComponentType<Integer> ITEM_OXIDATION = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(WildsRegrown.modid, "item_oxidation"),
            ComponentType.<Integer>builder().codec(Codec.INT).build());

    public static final ComponentType<Integer> ITEM_SHARPNESS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(WildsRegrown.modid, "item_sharpness"),
            ComponentType.<Integer>builder().codec(Codec.INT).build());

    public static final ComponentType<Integer> ITEM_STANCE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(WildsRegrown.modid, "item_stance"),
            ComponentType.<Integer>builder().codec(Codec.INT).build());

    //Block Properties
    public static final ComponentType<Integer> FUEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(modid, "fuel"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
    public static final ComponentType<Boolean> LIT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(modid, "lit"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void initialize() {}
}
