package com.wildsregrown.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ToolEventInput(int stance, ItemStack tool, ItemStack material) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
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