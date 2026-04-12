package com.wildsregrown.world.decorator;

import com.sipke.api.chunk.tile.Aquifer;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;
import com.sipke.api.chunk.Chunk;
import com.sipke.api.geology.Stratum;
import com.sipke.generator.WorldGenerator;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.fluids.SetAbleFluidState;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModFluids;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;

import java.util.LinkedList;
import java.util.Objects;

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.blocks.SoilBlock.*;

public class BaseDecorator implements Decorator {

    private final WRGChunkGenerator chunkGenerator;

    public BaseDecorator(WRGChunkGenerator chunkGenerator) {
        this.chunkGenerator = chunkGenerator;
    }

    //Fill chunk from Column Chunk
    public void apply(net.minecraft.world.chunk.Chunk chunk) {

        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.getStartX();
        int chunkZ = chunkPos.getStartZ();

        //Creating BlockPos Instance.
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        WorldGenerator generator = chunkGenerator.getWorld().generator;
        Chunk noiseChunk = generator.getNoiseChunk(chunkX, chunkZ);

        //Iterate Chunk positions
        for (int xi = 0; xi < chunkSize; ++xi) {
            for (int zi = 0; zi < chunkSize; ++zi) {

                int idx = xi * chunkSize + zi;
                int x = chunkX + xi;
                int y = MathUtil.floor(noiseChunk.getTile(idx).height);
                int z = chunkZ + zi;

                int waterLevel = MathUtil.ceil(noiseChunk.getTile(idx).waterLevel);

                //Set Blockpos
                blockPos.setX(x);
                blockPos.setZ(z);

                LinkedList<Stratum> strata = noiseChunk.getColumn(idx).getStrata();
                for (int i = 0; i < strata.size(); i++) {
                    Stratum stratum = strata.get(i);

                    String key = stratum.getRegistry().name;
                    Block block = Registries.BLOCK.get(Identifier.of(WildsRegrown.modid, key));
                    boolean air = Objects.equals(key, "air");
                    if (air){
                        block = Blocks.AIR;
                    }else if(!Registries.BLOCK.containsId(Identifier.of(WildsRegrown.modid, key))){
                        WildsRegrown.LOGGER.info("Missing entry: " + key);
                    }

                    for (int k = stratum.getFloor(); k < stratum.getCeil(); k++) {
                        blockPos.setY(k);
                        setBlock(chunk, blockPos, block);
                    }

                    if (air){
                        if (!WorldRegistries.MATERIALS.isSoil(strata.get(MathUtil.max(i-1, 0)).getKey())) {
                            Block floor = Registries.BLOCK.get(Identifier.of(WildsRegrown.modid, WorldRegistries.MATERIALS.get(strata.get(MathUtil.max(i-1, 0)).getKey()).name));
                            setLayer(stratum.floor, chunk, blockPos, floor.getDefaultState(), false, waterLevel);
                        }
                        if(i != strata.size()-1) {
                            if (!WorldRegistries.MATERIALS.isSoil(strata.get(i + 1).getKey())) {
                                Block ceil = Registries.BLOCK.get(Identifier.of(WildsRegrown.modid, WorldRegistries.MATERIALS.get(strata.get(i + 1).getKey()).name));
                                setLayer(stratum.ceil(), chunk, blockPos, ceil.getDefaultState(), true, waterLevel);
                            }
                        }
                    }

                }

                fillAirLayer(chunk, blockPos, 0, waterLevel, Blocks.WATER);

                //layered Map
                Stratum stratum = strata.getLast();
                BlockState state = Registries.BLOCK.get(Identifier.of(WildsRegrown.modid, WorldRegistries.MATERIALS.get(stratum.geoKey).name)).getDefaultState();
                blockPos.setY(y);
                int layers = setLayer(stratum.ceil(), chunk, blockPos, state,false, waterLevel);
                drawTopLayer(noiseChunk, chunk, layers == 0 ? blockPos.down() : blockPos, idx);

                /// Aquifers
                for (Aquifer aquifer : noiseChunk.getColumn(idx).getAquifers()) {

                    FluidState fluidState = ModFluids.SWEET_WATER.getDefaultState()
                            .with(Properties.FALLING, false)
                            .with(FlowableFluid.LEVEL, 8)
                            .with(ModProperties.vecX, aquifer.getX())
                            .with(ModProperties.vecZ, aquifer.getZ());

                    for (int i = aquifer.from(); i <= aquifer.too(); i++) {

                        blockPos.setY(i);
                        BlockState currentState = chunk.getBlockState(blockPos);

                        if (currentState.isAir()){
                            chunk.setBlockState(blockPos, fluidState.getBlockState());
                        }else if (currentState.contains(WATERLOGGED)){
                            ((SetAbleFluidState)state).wrg$setFluidState(fluidState);
                            //chunk.setBlockState(blockPos, currentState.with(WATERLOGGED, true));
                        }

                    }

                    blockPos.setY((int)aquifer.too()+1);
                    fluidState = fluidState
                            .with(FlowableFluid.LEVEL, aquifer.getLevel())
                            .with(ModProperties.vecX, aquifer.getX())
                            .with(ModProperties.vecZ, aquifer.getZ());
                    state = chunk.getBlockState(blockPos);
                    if (state.isAir()) {
                        WildsRegrown.LOGGER.info(fluidState.toString());
                        chunk.setBlockState(blockPos, fluidState.getBlockState());
                    }else if (state.contains(WATERLOGGED)){
                        //((SetAbleFluidState)state).wrg$setFluidState(fluidState);
                        //chunk.setBlockState(blockPos, state.with(WATERLOGGED, true));
                    }

                }

                //drawOverlay(chunk, blockPos.setY(y+2), noiseChunk.getTile(idx).erosionMask);

            }//for x
        }//for z
    }

    private void drawTopLayer(Chunk noiseChunk, net.minecraft.world.chunk.Chunk chunk, BlockPos blockPos, int idx) {
        BlockState state = chunk.getBlockState(blockPos);
        if (state.contains(MOISTURE)){
            state = state.with(MOISTURE, MathUtil.round(MathUtil.range(noiseChunk.getTile(idx).moisture,0, 16)));
        }
        if (state.contains(OVERGROWN)){
            state = state.with(OVERGROWN, noiseChunk.getColumn(idx).getOvergrown());
        }
        chunk.setBlockState(blockPos, state, 2);
    }

    private int setLayer(float y, net.minecraft.world.chunk.Chunk chunk, BlockPos.Mutable pos, BlockState state, boolean invert, float waterLevel) {

        //layered Map
        int h = MathUtil.floor(y);
        float delta = MathUtil.float01(y - h);
        int layers = MathUtil.clamp(MathUtil.round(delta * 8f), 0, 8);
        //WildsRegrown.LOGGER.info(delta + " / " + layers);
        if (layers > 0) {

            if (state.contains(LAYERS)) {
                state = state.with(LAYERS, MathUtil.clamp(layers, 1,8));
            }
            if (invert){
                if (state.contains(Properties.FACING)) {
                    state = state.with(Properties.FACING, Direction.DOWN);
                }
            }
            if (y-1 < waterLevel){
                if(state.contains(Properties.WATERLOGGED)){
                    state = state.with(Properties.WATERLOGGED, true);
                }
            }
            pos.setY((int) y + (invert ? -1 : 0));
            chunk.setBlockState(pos, state, 2);

        }

        return layers;
    }

    private void drawOverlay(net.minecraft.world.chunk.Chunk chunk, BlockPos pos, float mask) {
        
        if(mask> 0.8){
            chunk.setBlockState(pos, Blocks.RED_STAINED_GLASS.getDefaultState(), 2);
        }else
        if(mask > 0.6){
            chunk.setBlockState(pos, Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 2);
        }else
        if(mask > 0.4){
            chunk.setBlockState(pos, Blocks.BROWN_STAINED_GLASS.getDefaultState(), 2);
        }else
        if(mask > 0.2){
            chunk.setBlockState(pos, Blocks.GLASS.getDefaultState(), 2);
        }else {
            chunk.setBlockState(pos, Blocks.WHITE_STAINED_GLASS.getDefaultState(), 2);
        }
    }

}