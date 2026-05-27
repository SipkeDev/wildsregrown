package com.wildsregrown.render.item.property;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.registries.ModComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record ItemOxidationProperty() implements RangeSelectItemModelProperty {

    public static final MapCodec<ItemOxidationProperty> CODEC = MapCodec.unit(new ItemOxidationProperty());

    public ItemOxidationProperty() {}

    @Override
    public float get(ItemStack stack, @org.jspecify.annotations.Nullable ClientLevel world, @org.jspecify.annotations.Nullable ItemOwner context, int seed) {
        if (stack.getComponents().has(ModComponents.ITEM_OXIDATION)) {
            return stack.getComponents().get(ModComponents.ITEM_OXIDATION);
        }
        return 0f;
    }

    public MapCodec<ItemOxidationProperty> type() {
        return CODEC;
    }
}

