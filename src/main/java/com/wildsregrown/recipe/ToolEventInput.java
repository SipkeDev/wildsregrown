package com.wildsregrown.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record ToolEventInput(int stance, ItemStack tool, ItemStack material) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 2) {
            return material;
        }
        return tool;
    }

    @Override
    public int size() {
        return 1;
    }

}