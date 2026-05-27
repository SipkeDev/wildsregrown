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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import wildsregrown.api.block.materials.SoilBlock;
import wildsregrown.api.block.materials.StoneBlock;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {

        TagAppender<Block, Block> soil = valueLookupBuilder(BlockTags.DIRT);
        TagAppender<Block, Block> stone = valueLookupBuilder(BlockTags.BASE_STONE_OVERWORLD);
        TagAppender<Block, Block> wood = valueLookupBuilder(BlockTags.LOGS);

        for (Block block : BuiltInRegistries.BLOCK){

            if (block instanceof StoneBlock){
                stone.add(block)
                        .addOptionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                        .setReplace(true);
            }

            if (block instanceof SoilBlock){
                soil.add(block)
                        .addOptionalTag(BlockTags.AZALEA_GROWS_ON)
                        .addOptionalTag(BlockTags.AZALEA_ROOT_REPLACEABLE)
                        .addOptionalTag(BlockTags.MINEABLE_WITH_SHOVEL)
                        .setReplace(true);
            }

            if (block instanceof Log || block instanceof HalfLog || block instanceof BeamSupport || block instanceof Planks){
                wood.add(block)
                        .addOptionalTag(BlockTags.MINEABLE_WITH_AXE);
            }

        }

    }

}