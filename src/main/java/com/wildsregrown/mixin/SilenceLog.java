package com.wildsregrown.mixin;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ServerRecipeManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(ServerRecipeManager.class)
public abstract class SilenceLog {
    @Redirect(method = "method_64989", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Recipe;isIgnoredInRecipeBook()Z", opcode = Opcodes.GETFIELD))
    private static boolean missingTextureReferences(final Recipe instance) {
        return true;
    }
}