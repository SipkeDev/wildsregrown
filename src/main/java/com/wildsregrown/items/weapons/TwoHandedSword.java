package com.wildsregrown.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.jspecify.annotations.Nullable;

public class TwoHandedSword extends Item {

    public TwoHandedSword(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(settings.sword(material, attackDamage, attackSpeed));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof PlayerEntity player) {
            ItemStack offhandItem = player.getStackInHand(Hand.OFF_HAND);
            player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            player.giveOrDropStack(offhandItem);
        }
    }

}