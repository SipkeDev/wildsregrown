package com.wildsregrown.items.weapons;

import com.wildsregrown.items.IRadialItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class Sword extends Item implements IRadialItem {

    public Sword(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material.applySwordSettings(settings, attackDamage, attackSpeed));
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

}
