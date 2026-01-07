package com.wildsregrown.data;

import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.StoneBlock;
import com.wildsregrown.blocks.wood.*;
import com.wildsregrown.blocks.wood.tree.HalfLog;
import com.wildsregrown.blocks.wood.tree.Log;
import com.wildsregrown.blocks.wood.tree.TreeBranch;
import com.wildsregrown.registries.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /**
        //Add tags here
        FabricTagProvider<Block>.FabricTagBuilder tag;

        //Climbable
        tag = getOrCreateTagBuilder(BlockTags.CLIMBABLE);
        ModBlocks.CLIMBABLE.forEach((tag::add));

        for (Block block : Registries.BLOCK){

            if (block instanceof SoilBlock){
                getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(block);
                getOrCreateTagBuilder(BlockTags.VALID_SPAWN).add(block);
                getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON).add(block);
                getOrCreateTagBuilder(BlockTags.AZALEA_GROWS_ON).add(block);
                getOrCreateTagBuilder(BlockTags.DIRT).add(block);
            }

            if (block instanceof Layered || block instanceof StoneBlock){
                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
                getOrCreateTagBuilder(BlockTags.VALID_SPAWN).add(block);
                getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON).add(block);
            }

            if (block instanceof Log || block instanceof Planks || block instanceof HalfLog || block instanceof TreeBranch || block instanceof Beam){
                getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
            }

        }
         */

    }

}