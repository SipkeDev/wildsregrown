package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static final TagKey<Block> portable_crafting = TagKey.of(RegistryKeys.BLOCK, Identifier.of(WildsRegrown.modid, "portable_crafting"));
    public static final TagKey<Block> simple_crafting = TagKey.of(RegistryKeys.BLOCK, Identifier.of(WildsRegrown.modid, "simple_crafting"));
    public static final TagKey<Block> artisan_crafting = TagKey.of(RegistryKeys.BLOCK, Identifier.of(WildsRegrown.modid, "artisan_crafting"));

    public static final TagKey<Item> whetstone = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "whetstone"));

    public static final TagKey<Item> stone_crafting_materials = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "stone_crafting_materials"));
    public static final TagKey<Item> wood_crafting_materials = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "wood_crafting_materials"));

    public static final TagKey<Item> wood_slab = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "wood_slab"));

    public static final TagKey<Item> stone_chisel = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "stone_chisel"));
    public static final TagKey<Item> wood_chisel = TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "wood_chisel"));

}
