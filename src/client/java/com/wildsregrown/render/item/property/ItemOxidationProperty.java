package com.wildsregrown.render.item.property;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.registries.ModComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record ItemOxidationProperty() implements NumericProperty {

    public static final MapCodec<ItemOxidationProperty> CODEC = MapCodec.unit(new ItemOxidationProperty());

    public ItemOxidationProperty() {}

    @Override
    public float getValue(ItemStack stack, @org.jspecify.annotations.Nullable ClientWorld world, @org.jspecify.annotations.Nullable HeldItemContext context, int seed) {
        if (stack.getComponents().contains(ModComponents.ITEM_OXIDATION)) {
            return stack.getComponents().get(ModComponents.ITEM_OXIDATION);
        }
        return 0f;
    }

    public MapCodec<ItemOxidationProperty> getCodec() {
        return CODEC;
    }
}

