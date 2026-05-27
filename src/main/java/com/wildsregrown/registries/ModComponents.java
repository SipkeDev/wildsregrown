package com.wildsregrown.registries;

import com.mojang.serialization.Codec;
import com.wildsregrown.WildsRegrown;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.food.FoodProperties;

import static com.wildsregrown.WildsRegrown.modid;

public class ModComponents {

    //Crafting registeries
    @Deprecated
    public static final DataComponentType<String> IN_WORLD_RESULT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(WildsRegrown.modid, "result"),
            DataComponentType.<String>builder().persistent(Codec.STRING).build());

    //Item Properties
    public static final DataComponentType<Integer> ITEM_OXIDATION = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(WildsRegrown.modid, "item_oxidation"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final DataComponentType<Integer> ITEM_SHARPNESS = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(WildsRegrown.modid, "item_sharpness"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    //Block Properties
    public static final DataComponentType<Integer> FUEL = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(modid, "fuel"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );
    public static final DataComponentType<Boolean> LIT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(modid, "lit"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );

    public static final FoodProperties WILD_FOOD = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.25F).build();
    public static final FoodProperties APPLE = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.5F).build();


    public static void initialize() {
        WildsRegrown.LOGGER.info("Init WRG components");
    }
}
