package com.wildsregrown.data.recipes;

import com.wildsregrown.recipe.ToolEventRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ToolEventBuilder implements RecipeBuilder {

    private final Ingredient tool;
    private final int stance;
    private final Ingredient material;
    private final ItemStack output;

    public ToolEventBuilder(HolderGetter<Item> registries, TagKey<Item> tool, int stance, Block material, Block output){
        this.tool = Ingredient.of(registries.getOrThrow(tool));
        this.stance = stance;
        this.material = Ingredient.of(material.asItem());
        this.output = output.asItem().getDefaultInstance();
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.output.getItem();
    }

    @Override
    public void save(RecipeOutput exporter, ResourceKey<Recipe<?>> recipeKey) {
        exporter.accept(recipeKey, new ToolEventRecipe(stance, tool, material, output), null);
    }

}
