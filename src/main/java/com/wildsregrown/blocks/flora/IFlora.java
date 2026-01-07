package com.wildsregrown.blocks.flora;

import com.sipke.core.pos.INeighbours;
import com.sipke.core.sampler.jitter.RoundJitter;
import com.sipke.core.vector.Vec2;
import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;

public interface IFlora extends INeighbours {

    default boolean retrieveMoisture(ServerWorld world, BlockState state, BlockPos pos){
        BlockPos soilPos = new BlockPos(pos).down();
        BlockState soil = world.getBlockState(soilPos);
        if (soil.getBlock() instanceof SoilBlock){
            int soilWater = soil.get(ModProperties.MOISTURE);
            if (soilWater > 1) {
                int floraWater = state.get(ModProperties.MOISTURE);
                if (floraWater < 16) {
                    world.setBlockState(pos, state.with(ModProperties.MOISTURE, floraWater + 1));
                    world.setBlockState(soilPos, soil.with(ModProperties.MOISTURE, soilWater - 1));
                }
            }
            return true;
        }
        return false;
    }

    default void spreadDisease(ServerWorld world, BlockState state, BlockPos pos, Random random, FloraStage disease, float survivalChance) {
        for (int i = 0; i < Dice.d12(random); i++) {
            int indice = random.nextInt(8);
            BlockPos newPos = new BlockPos(
                    pos.getX() + (nx[indice] * Dice.d8(random)),
                    pos.getY() + snx[random.nextInt(3)],
                    pos.getZ() + (nz[indice] * Dice.d8(random))
            );
            BlockState newState = world.getBlockState(newPos);
            if (newState.getBlock() instanceof IFlora) {
                WildsRegrown.LOGGER.info("SPREAD DISEASE");
                world.setBlockState(newPos, newState.with(ModProperties.FLORAL_STAGE, disease));
            }
        }
    }

    default void spreadSeed(ServerWorld world, BlockPos pos, Random random, int range) {

        Vec2 vec = new Vec2(pos.getX(), pos.getZ());
        RoundJitter.apply(vec, random.nextInt(), random.nextInt(range));
        int dx = MathUtil.round(vec.x);
        int dz = MathUtil.round(vec.y);
        BlockPos newPos = new BlockPos(dx, world.getTopY(Heightmap.Type.WORLD_SURFACE, dx, dz), dz);

        populate(world, newPos, world.getBlockState(pos));
    }

    default void invade(ServerWorld world, BlockPos pos, Random random) {

        BlockState state = world.getBlockState(pos).getBlock().getDefaultState();
        BlockPos.Mutable newPos = new BlockPos.Mutable();

        int i = random.nextInt(8);
        newPos.setX(pos.getX()+nx[i]);
        newPos.setY(pos.getY()+random.nextInt(3)-1);
        newPos.setZ(pos.getZ()+nz[i]);

        populate(world, newPos, state);
    }

    default void populate(ServerWorld world, BlockPos pos, BlockState state) {
        if (isValidPos(world, pos)) {
            world.setBlockState(pos, setLayers(world, pos, state), 2);
        }
    }

    default boolean isValidPos(ServerWorld world, BlockPos pos){
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT);
    }

    default BlockState setLayers(ServerWorld world, BlockPos pos, BlockState state){
        BlockState target = world.getBlockState(pos.down());
        if (target.contains(ModProperties.LAYERS) && state.contains(ModProperties.LAYERS)){
            int i = target.get(ModProperties.LAYERS);
            state = state.with(ModProperties.LAYERS, i);
        }
        return state;
    }

}
