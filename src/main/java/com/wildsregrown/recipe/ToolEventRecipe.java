package com.wildsregrown.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public record ToolEventRecipe(int stance, Ingredient tool, Ingredient material, ItemStack output) implements Recipe<ToolEventInput> {

    @Override
    public boolean matches(ToolEventInput input, World world) {
        if (stance == input.stance()) {
            return tool.test(input.tool()) && material.test(input.material());
        }
        return false;
    }

    @Override
    public ItemStack craft(ToolEventInput input, RegistryWrapper.WrapperLookup registries) {
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
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
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
        public PacketCodec<RegistryByteBuf, ToolEventRecipe> packetCodec() {
            return PacketCodec.tuple(
                    PacketCodecs.INTEGER, ToolEventRecipe::stance,
                    Ingredient.PACKET_CODEC, ToolEventRecipe::tool,
                    Ingredient.PACKET_CODEC, ToolEventRecipe::material,
                    ItemStack.PACKET_CODEC, ToolEventRecipe::output,
                    ToolEventRecipe::new);
        }
    }

}
