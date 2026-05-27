package com.wildsregrown.items.weapons;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.Nullable;

public class TwoHandedSword extends Item {

    public TwoHandedSword(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(settings.sword(material, attackDamage, attackSpeed));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof Player player) {
            ItemStack offhandItem = player.getItemInHand(InteractionHand.OFF_HAND);
            player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            player.handleExtraItemsCreatedOnUse(offhandItem);
        }
    }

}