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

public class ToolWorkBenchEventBuilder implements CraftingRecipeJsonBuilder {

    private final Ingredient tool;
    private final int stance;
    private final Ingredient material;
    private final ItemStack output;

    public ToolWorkBenchEventBuilder(RegistryEntryLookup<Item> registries, int stance, TagKey<Item> tool, Block material, Block workbench, Block output){
        this.tool = Ingredient.ofTag(registries.getOrThrow(tool));
        this.material = Ingredient.ofItem(material.asItem());
        this.stance = stance;
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
