package com.wildsregrown.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record ToolEventRecipe(int stance, Ingredient tool, Ingredient material, ItemStack output) implements Recipe<ToolEventInput> {

    @Override
    public boolean matches(ToolEventInput input, Level world) {
        if (stance == input.stance()) {
            return tool.test(input.tool()) && material.test(input.material());
        }
        return false;
    }

    @Override
    public ItemStack assemble(ToolEventInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<ToolEventInput>> getSerializer() {
        return ModRecipes.toolEventRecipe;
    }

    @Override
    public RecipeType<? extends Recipe<ToolEventInput>> getType() {
        return ModRecipes.toolEventType;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
    }

    public static class Serializer implements RecipeSerializer<ToolEventRecipe> {
        @Override
        public MapCodec<ToolEventRecipe> codec() {
            return RecordCodecBuilder.mapCodec(inst -> inst.group(
                    Codec.INT.fieldOf("stance").forGetter(ToolEventRecipe::stance),
                    Ingredient.CODEC.fieldOf("tool").forGetter(ToolEventRecipe::tool),
                    Ingredient.CODEC.fieldOf("material").forGetter(ToolEventRecipe::material),
                    ItemStack.CODEC.fieldOf("result").forGetter(ToolEventRecipe::output)).apply(inst, ToolEventRecipe::new));
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ToolEventRecipe> streamCodec() {
            return StreamCodec.composite(
                    ByteBufCodecs.INT, ToolEventRecipe::stance,
                    Ingredient.CONTENTS_STREAM_CODEC, ToolEventRecipe::tool,
                    Ingredient.CONTENTS_STREAM_CODEC, ToolEventRecipe::material,
                    ItemStack.STREAM_CODEC, ToolEventRecipe::output,
                    ToolEventRecipe::new);
        }
    }

}
