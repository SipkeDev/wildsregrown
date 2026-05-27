package com.wildsregrown.data.tags;

import com.wildsregrown.blocks.carpentry.framing.beam.BeamSupport;
import com.wildsregrown.blocks.carpentry.Planks;
import com.wildsregrown.blocks.carpentry.tree.HalfLog;
import com.wildsregrown.blocks.carpentry.tree.Log;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import wildsregrown.api.block.materials.SoilBlock;
import wildsregrown.api.block.materials.StoneBlock;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {

    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void addTags(HolderLookup.Provider registrywrapper) {

        TagAppender<Item, Item> soil = valueLookupBuilder(ItemTags.DIRT);
        TagAppender<Item, Item> stone = valueLookupBuilder(ItemTags.STONE_CRAFTING_MATERIALS);

        TagAppender<Item, Item> wood = valueLookupBuilder(ItemTags.WOODEN_TOOL_MATERIALS);


        for (Block block : BuiltInRegistries.BLOCK){

            if (block instanceof StoneBlock){
                stone.add(block.asItem());
            }

            if (block instanceof SoilBlock){
                soil.add(block.asItem());
            }

            if (block instanceof Log || block instanceof HalfLog || block instanceof BeamSupport || block instanceof Planks){
                wood.add(block.asItem());
            }

        }

    }

}