package com.wildsregrown.items.weapons;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class Sword extends Item {

    public Sword(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
        super(material.applySwordProperties(settings, attackDamage, attackSpeed));
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

}
