package com.wildsregrown.world.decorator;

import com.sipke.api.chunk.Chunk;
import com.sipke.api.features.structures.StructurePiece;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.StructureWorldAccess;

import java.util.*;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;

public class StructureDecorator implements Decorator {

    private final WRGChunkGenerator chunkGenerator;

    public StructureDecorator(WRGChunkGenerator chunkGenerator){
        this.chunkGenerator = chunkGenerator;
    }

    //Fill chunk from Column Chunk
    public void apply(StructureWorldAccess world, net.minecraft.world.chunk.Chunk chunk) {

        ChunkPos chunkPos = chunk.getPos();
        Chunk noiseChunk = chunkGenerator.getWorld().generator.getNoiseChunk(chunkPos.getStartX(), chunkPos.getStartZ());
        if (noiseChunk.hasStructure()) {
            for (StructurePiece piece : noiseChunk.getStructures()) {

                if (piece == null){continue;}

                //LOGGER.info("Chunk structs: " + noiseChunk.getStructures().size());

                //Creating BlockPos Instance.
                BlockPos pos = new BlockPos(
                        piece.getX(),
                        piece.getY(),
                        piece.getZ()
                );

                BlockRotation rotation = switch (piece.getRotation()) {
                    case 2 -> BlockRotation.CLOCKWISE_90;
                    case 3 -> BlockRotation.CLOCKWISE_180;
                    case 0 -> BlockRotation.COUNTERCLOCKWISE_90;
                    default -> BlockRotation.NONE;
                };

                StructurePlacementData data = new StructurePlacementData();
                data.setRotation(rotation);
                data.setMirror(BlockMirror.NONE);
                Optional<StructureTemplate> optional = world.toServerWorld().getStructureTemplateManager().getTemplate(Identifier.of(modid, piece.getId()));

                if (optional.isEmpty()) {
                    LOGGER.error("No matching template: " + piece.getId());
                    continue;
                }

                StructureTemplate template = optional.get();
                template.place(world, pos, pos, data, world.getRandom(), 19);

                List<StructureTemplate.StructureBlockInfo> structureBlocks = template.getInfosForBlock(pos, data, ModBlocks.structureBlock);
                for (StructureTemplate.StructureBlockInfo block : structureBlocks){
                    world.setBlockState(block.pos(), Blocks.AIR.getDefaultState(), 19);
                }

            }
        }

    }

}