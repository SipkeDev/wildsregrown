package com.wildsregrown.world.decorator;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public interface Decorator {

    default void fillAirLayer(Chunk chunk, BlockPos.Mutable blockPos, int start, int end, Block block){
        for(int i = start; i <= end; ++i){
            blockPos.setY(i);
            if (chunk.getBlockState(blockPos).isAir()) {
                setBlock(chunk, blockPos, block);
            }
        }
    }

    default void fillLayer(Chunk chunk, BlockPos.Mutable blockPos, int start, int end, Block block){
        for(int i = start; i <= end; ++i){
            blockPos.setY(i);
            setBlock(chunk, blockPos, block);
        }
    }

    default void setBlock(Chunk chunk, BlockPos.Mutable blockPos, Block block){
        chunk.setBlockState(blockPos, block.getDefaultState(), 2);
    }

}
