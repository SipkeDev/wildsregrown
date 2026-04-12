package com.wildsregrown.data;

import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.StoneBlock;
import com.wildsregrown.blocks.wood.Beam;
import com.wildsregrown.blocks.wood.Planks;
import com.wildsregrown.blocks.wood.tree.HalfLog;
import com.wildsregrown.blocks.wood.tree.Log;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.tag.ProvidedTagBuilder;
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
    protected void configure(RegistryWrapper.WrapperLookup registrywrapper) {

        ProvidedTagBuilder<Item, Item> soil = valueLookupBuilder(ItemTags.DIRT);
        ProvidedTagBuilder<Item, Item> stone = valueLookupBuilder(ItemTags.STONE_CRAFTING_MATERIALS);

        ProvidedTagBuilder<Item, Item> wood = valueLookupBuilder(ItemTags.WOODEN_TOOL_MATERIALS);


        for (Block block : Registries.BLOCK){

            if (block instanceof StoneBlock){
                stone.add(block.asItem());
            }

            if (block instanceof SoilBlock){
                soil.add(block.asItem());
            }

            if (block instanceof Log || block instanceof HalfLog || block instanceof Beam || block instanceof Planks){
                wood.add(block.asItem());
            }

        }

    }

}