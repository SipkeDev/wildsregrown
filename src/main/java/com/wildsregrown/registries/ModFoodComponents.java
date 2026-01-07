package com.wildsregrown.registries;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent WILD_FOOD = (new FoodComponent.Builder()).nutrition(3).saturationModifier(0.25F).build();
    public static final FoodComponent APPLE = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.5F).build();

}
