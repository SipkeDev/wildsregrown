package com.wildsregrown.world.decorator;

import com.sipke.api.chunk.Chunk;
import com.sipke.api.features.flora.FloraPos;
import com.sipke.math.MathUtil;
import com.sipke.registeries.Floras;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;

import static com.sipke.WorldConstants.chunkSize;

public class FloraDecorator {

    private final WRGChunkGenerator chunkGenerator;

    public FloraDecorator(WRGChunkGenerator chunkGenerator){
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

        //Flora
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {

                int idx = i*chunkSize+j;
                FloraPos pos = noiseChunk.getColumn(idx).getFlora();

                if (pos.key() != 0) {
                    final float height = noiseChunk.getTile(idx).height;
                    int layers = Math.round((height % 1) * 8f);

                    blockPos.setY(layers == 0 ? (int) height: (int) height + 1);
                    blockPos.setX(globalX + i);
                    blockPos.setZ(globalZ + j);

                    layers = layers == 0 ? 8 : layers;

                    Identifier identifier = Identifier.of(WildsRegrown.modid, Floras.get(pos.key()).name());
                    Block block = Registries.BLOCK.get(identifier);

                    if (!Registries.BLOCK.containsId(identifier)) {
                        //WildsRegrown.LOGGER.info("Missing flora regist: " + Floras.get(pos.getKey()).getName());
                        block = ModBlocks.grass;
                    }

                    BlockState state = block.getDefaultState();
                    state = state.with(ModProperties.LAYERS, layers);

                    if (state.contains(Properties.WATERLOGGED)){
                        if (blockPos.getY() <= noiseChunk.getTile(idx).waterLevel){
                            state.with(Properties.WATERLOGGED, true);
                        }
                    }

                    //Set Age
                    if (state.contains(ModProperties.AGE_4)){
                        state = state.with(ModProperties.AGE_4, MathUtil.round(pos.age()*(ModProperties.AGE_4.getValues().size()-1)));
                    }
                    if (state.contains(ModProperties.AGE_6)){
                        state = state.with(ModProperties.AGE_6, MathUtil.round(pos.age()*(ModProperties.AGE_6.getValues().size()-1)));
                    }

                    //Set Flora State
                    if (state.contains(ModProperties.MOISTURE)) {
                        int m = MathUtil.round(MathUtil.range(noiseChunk.getTile(idx).moisture, 1, 16));
                        state = state.with(ModProperties.MOISTURE, m);
                    }
                    if (pos.flowering()){
                        state = state.with(ModProperties.FLORAL_STAGE, FloraStage.FLOWERING);
                    }

                    world.setBlockState(blockPos, state, 19);
                }
            }
        }
    }

}
