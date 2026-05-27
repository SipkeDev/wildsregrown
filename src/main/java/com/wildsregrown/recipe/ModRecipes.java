package com.wildsregrown.recipe;

import com.wildsregrown.WildsRegrown;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {

    public static final RecipeSerializer<ToolEventRecipe> toolEventRecipe = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "tool_event"), new ToolEventRecipe.Serializer());
    public static final RecipeType<ToolEventRecipe> toolEventType = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "tool_event"), new RecipeType<ToolEventRecipe>() {
        @Override
        public String toString() {
            return "tool_event";
        }
    });

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering Recipes");
    }

}
