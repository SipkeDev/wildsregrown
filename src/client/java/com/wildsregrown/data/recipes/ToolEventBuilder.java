package com.wildsregrown.data.recipes;

import com.wildsregrown.recipe.ToolEventRecipe;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

public class ToolEventBuilder implements CraftingRecipeJsonBuilder {

    private final Ingredient tool;
    private final int stance;
    private final Ingredient material;
    private final ItemStack output;

    public ToolEventBuilder(RegistryEntryLookup<Item> registries, TagKey<Item> tool, int stance, Block material, Block output){
        this.tool = Ingredient.ofTag(registries.getOrThrow(tool));
        this.stance = stance;
        this.material = Ingredient.ofItem(material.asItem());
        this.output = output.asItem().getDefaultStack();
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output.getItem();
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        exporter.accept(recipeKey, new ToolEventRecipe(stance, tool, material, output), null);
    }

}
