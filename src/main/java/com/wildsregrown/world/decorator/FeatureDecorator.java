package com.wildsregrown.world.decorator;

import com.sipke.api.chunk.Chunk;
import com.sipke.api.chunk.Column;
import com.sipke.api.features.Feature;
import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.api.features.trees.spawn.TreePos;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.wood.tree.TreeEntity;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;

import java.util.ArrayList;

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.WildsRegrown.modid;

public class FeatureDecorator {

    private final WRGChunkGenerator chunkGenerator;

    public FeatureDecorator(WRGChunkGenerator chunkGenerator){
        this.chunkGenerator = chunkGenerator;
    }

    //Fill chunk from Column Chunk
    public void apply(StructureWorldAccess world, net.minecraft.world.chunk.Chunk chunk) {

        ChunkPos chunkPos = chunk.getPos();
        int globalX = chunkPos.getStartX();
        int globalZ = chunkPos.getStartZ();

        //Creating BlockPos Instance.
        BlockPos.Mutable blockPos = new BlockPos.Mutable();
        Chunk noiseChunk = chunkGenerator.getWorld().generator.getNoiseChunk(globalX, globalZ);

        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                int idx = i * chunkSize + j;
                blockPos.set(globalX + i, 0, globalZ + j);
                Column column = noiseChunk.getColumn(idx);
                for (Feature feature : column.getFeatures()) {
                    if (feature == null) {continue;}
                    Block block = Registries.BLOCK.get(Identifier.of(modid, feature.getIndentifier()));
                    for (int k = feature.getMinY(); k < feature.getMaxY(); k++) {
                        blockPos.setY(k);
                        world.setBlockState(blockPos, block.getDefaultState(), 2);
                    }
                }
            }
        }

    }

}
