package com.wildsregrown.world.decorator;

import com.sipke.api.chunk.Chunk;
import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.api.features.trees.spawn.TreePos;
import com.sipke.math.MathUtil;
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

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.WildsRegrown.modid;

public class TreeDecorator {

    private final WRGChunkGenerator chunkGenerator;

    public TreeDecorator(WRGChunkGenerator chunkGenerator){
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

        //trees, Iterate Chunk positions
        for (TreePos pos : noiseChunk.getTrees()) {
            TreeConfig config = pos.getConfig();

            int dx = pos.getChunkX();
            int dz = pos.getChunkZ();
            int idx = dx * chunkSize + dz;

            blockPos.set(
                    dx+globalX,
                    noiseChunk.getTile(idx).height,
                    dz+globalZ
            );

            Block block = Registries.BLOCK.get(Identifier.of(modid, config.name + "_source"));
            world.setBlockState(blockPos, block.getDefaultState(), 2);

            if (!Registries.BLOCK.containsId(Identifier.of(modid, config.name + "_source"))){
                WildsRegrown.LOGGER.info("INVALID TREE SOURCE: " + config.name);
            }

            if (world.getBlockEntity(blockPos) instanceof TreeEntity source){
                source.spawn(pos.getKey(), pos.getSeed(), pos.getGraph(), world);
            }

        }
    }

}
