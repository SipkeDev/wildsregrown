package com.wildsregrown.data;

import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.StoneBlock;
import com.wildsregrown.blocks.wood.*;
import com.wildsregrown.blocks.wood.tree.HalfLog;
import com.wildsregrown.blocks.wood.tree.Log;
import com.wildsregrown.registries.ModItems;
import com.wildsregrown.registries.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {

    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        //Add tags here
        //FabricTagProvider<Item>.FabricTagBuilder dirt = getOrCreateTagBuilder(ItemTags.DIRT);

        //FabricTagProvider<Item>.FabricTagBuilder stone_crafting_materials = getOrCreateTagBuilder(ModTags.stone_crafting_materials);
        //FabricTagProvider<Item>.FabricTagBuilder wood_crafting_materials = getOrCreateTagBuilder(ModTags.wood_crafting_materials);

        //FabricTagProvider<Item>.FabricTagBuilder planks = getOrCreateTagBuilder(ItemTags.PLANKS);
        //FabricTagProvider<Item>.FabricTagBuilder logs_a = getOrCreateTagBuilder(ItemTags.LOGS);
        //FabricTagProvider<Item>.FabricTagBuilder logs_b = getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN);

        for (Block block : Registries.BLOCK) {
            if (block instanceof Layered || block instanceof StoneBlock){
                //stone_crafting_materials.add(block.asItem());
            }
            if (block instanceof Planks || block instanceof PaintedStairs){
                //planks.add(block.asItem());
                //wood_crafting_materials.add(block.asItem());
            }
            if (block instanceof Log || block instanceof HalfLog){
                //logs_a.add(block.asItem());
                //logs_b.add(block.asItem());
                //wood_crafting_materials.add(block.asItem());
            }
            if (block instanceof SoilBlock){
                //dirt.add(block.asItem());
            }
        }

        /**
        FabricTagProvider<Item>.FabricTagBuilder tag = getOrCreateTagBuilder(ItemTags.PICKAXES);
        tag.add(ModItems.pickaxe_c45);
        tag.add(ModItems.pickaxe_c60);
        tag.add(ModItems.pickaxe_wootz);

        tag = getOrCreateTagBuilder(ItemTags.SHOVELS);
        tag.add(ModItems.iron_shovel);

        tag = getOrCreateTagBuilder(ItemTags.AXES);
        tag.add(ModItems.hatchet_c45);
        tag.add(ModItems.hatchet_c60);
        tag.add(ModItems.hatchet_wootz);

        tag = getOrCreateTagBuilder(ItemTags.HOES);
        tag.add(ModItems.hoe_c45);
        tag.add(ModItems.hoe_c60);
        tag.add(ModItems.hoe_wootz);

        tag = getOrCreateTagBuilder(ItemTags.SWORDS);
        tag.add(ModItems.sword_c45);
        tag.add(ModItems.sword_c60);
        tag.add(ModItems.sword_wootz);
        tag.add(ModItems.two_handed_sword_c45);
        tag.add(ModItems.two_handed_sword_c60);
        tag.add(ModItems.two_handed_sword_wootz);

        tag = getOrCreateTagBuilder(ModTags.stone_chisel);
        tag.add(ModItems.stone_chisel_c45);

        tag = getOrCreateTagBuilder(ModTags.wood_chisel);
        tag.add(ModItems.wood_chisel_c45);

        tag = getOrCreateTagBuilder(ModTags.whetstone);
        tag.add(ModItems.whetstone_coticule);

         */
    }

}