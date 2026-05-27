package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Block> portable_crafting = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "portable_crafting"));
    public static final TagKey<Block> simple_crafting = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "simple_crafting"));
    public static final TagKey<Block> artisan_crafting = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "artisan_crafting"));

    public static final TagKey<Item> whetstone = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "whetstone"));

    public static final TagKey<Item> stone_crafting_materials = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "stone_crafting_materials"));
    public static final TagKey<Item> wood_crafting_materials = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "wood_crafting_materials"));

    public static final TagKey<Item> wood_slab = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "wood_slab"));

    public static final TagKey<Item> stone_chisel = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "stone_chisel"));
    public static final TagKey<Item> wood_chisel = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "wood_chisel"));

}
