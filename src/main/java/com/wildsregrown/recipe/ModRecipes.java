package com.wildsregrown.recipe;

import com.wildsregrown.WildsRegrown;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<ToolEventRecipe> toolEventRecipe = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(WildsRegrown.modid, "tool_event"), new ToolEventRecipe.Serializer());
    public static final RecipeType<ToolEventRecipe> toolEventType = Registry.register(Registries.RECIPE_TYPE, Identifier.of(WildsRegrown.modid, "tool_event"), new RecipeType<ToolEventRecipe>() {
        @Override
        public String toString() {
            return "tool_event";
        }
    });

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering Recipes");
    }

}
