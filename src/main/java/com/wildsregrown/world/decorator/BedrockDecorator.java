package com.wildsregrown.world.decorator;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
public class BedrockDecorator implements Decorator {

    public void apply(Chunk chunk){

        ChunkPos chunkPos = chunk.getPos();
        int cx = chunkPos.getStartX();
        int cz = chunkPos.getStartZ();

        for(int xi = 0; xi < 16; ++xi) {
            for(int zi = 0; zi < 16; ++zi) {

                BlockPos.Mutable blockPos = new BlockPos.Mutable();

                int x = (cx + xi);
                int z = (cz + zi);
                int y = 0;

                blockPos.set(x, y, z);

                setBlock(chunk, blockPos, Blocks.BEDROCK);

            }

        }
    }

}
