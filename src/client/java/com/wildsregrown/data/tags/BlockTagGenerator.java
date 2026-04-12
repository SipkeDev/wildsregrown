package com.wildsregrown.data.tags;

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
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        ProvidedTagBuilder<Block, Block> soil = valueLookupBuilder(BlockTags.DIRT);
        ProvidedTagBuilder<Block, Block> stone = valueLookupBuilder(BlockTags.BASE_STONE_OVERWORLD);
        ProvidedTagBuilder<Block, Block> wood = valueLookupBuilder(BlockTags.LOGS);

        for (Block block : Registries.BLOCK){

            if (block instanceof StoneBlock){
                stone.add(block)
                        .addOptionalTag(BlockTags.PICKAXE_MINEABLE)
                        .setReplace(true);
            }

            if (block instanceof SoilBlock){
                soil.add(block)
                        .addOptionalTag(BlockTags.AZALEA_GROWS_ON)
                        .addOptionalTag(BlockTags.AZALEA_ROOT_REPLACEABLE)
                        .addOptionalTag(BlockTags.SHOVEL_MINEABLE)
                        .setReplace(true);
            }

            if (block instanceof Log || block instanceof HalfLog || block instanceof Beam || block instanceof Planks){
                wood.add(block)
                        .addOptionalTag(BlockTags.AXE_MINEABLE);
            }

        }

    }

}