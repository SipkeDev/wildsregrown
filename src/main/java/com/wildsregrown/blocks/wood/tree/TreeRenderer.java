package com.wildsregrown.blocks.wood.tree;

import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.api.features.trees.graph.TreeNode;
import com.sipke.math.CellType;
import com.sipke.math.Distance;
import com.sipke.math.HashUtil;
import com.sipke.math.MathUtil;
import com.sipke.noise2d.warp.DomainWarp;
import com.sipke.noise2d.warp.GradientWarp;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class TreeRenderer {

    static int flags = 2;
    static Random random = new Random(flags);

    static void renderSmallLeafCluster(WorldAccess world, BlockPos pos, BlockState state){
        BlockPos.Mutable mutable = pos.mutableCopy();

        mutable.set(pos.getX()-1, pos.getY(), pos.getZ());
        if (world.getBlockState(mutable).isAir()) {world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);}
        mutable.set(pos.getX()+1, pos.getY(), pos.getZ());
        if (world.getBlockState(mutable).isAir()) {world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);}
        mutable.set(pos.getX(), pos.getY(), pos.getZ()-1);
        if (world.getBlockState(mutable).isAir()) {world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);}
        mutable.set(pos.getX(), pos.getY(), pos.getZ()+1);
        if (world.getBlockState(mutable).isAir()) {world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);}
        mutable.set(pos.getX(), pos.getY()+1, pos.getZ());
        if (world.getBlockState(mutable).isAir()) {world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);}
    }

    static void renderDynamicLeafCluster(WorldAccess world, BlockPos pos, BlockState state, int radius){
        BlockPos.Mutable mutable = pos.mutableCopy();
        if (radius > 4){
            radius = 4;
        }
        int hashX = HashUtil.PrimeX;
        int hashY = HashUtil.PrimeY;
        int hashZ = HashUtil.PrimeZ;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    mutable.set(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                    if (world.getBlockState(mutable).isAir()) {
                        if (i * i + j * j + k * k <= radius * radius) {
                            if (CellType.cellValue.apply(HashUtil.hash(0, mutable.getX()+hashX, mutable.getY()+hashY, mutable.getZ()+hashZ),0) > 0.125f) {
                                world.setBlockState(mutable, state.with(ModProperties.MOISTURE, 6 + random.nextInt(8)), flags);
                                hashX+=HashUtil.PrimeX;
                                hashY+=HashUtil.PrimeY;
                                hashZ+=HashUtil.PrimeZ;
                            }
                        }
                    }
                }
            }
        }
    }

    static void removeDynamicLeafCluster(WorldAccess world, BlockPos pos, int radius){
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    if (i * i + j * j + k * k <= radius * radius) {
                        mutable.set(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                        if (world.getBlockState(mutable).getBlock() instanceof Leaves) {
                            world.setBlockState(mutable, Blocks.AIR.getDefaultState(), flags);
                        }
                    }
                }
            }
        }
    }

    static void renderTrunk(WorldAccess world, TreeNode node, BlockPos pos, BlockState state, boolean place){
        BlockPos.Mutable mutable = pos.mutableCopy();
        int r = MathUtil.floor(node.volume);
        if (r > 5){r = 5;}
        if (r == 0){
            world.setBlockState(mutable, state, flags);
        }else {
            for (int i = -r; i <= r; i++) {
                for (int j = -r; j <= r; j++) {
                    mutable.setX(pos.getX()+i);
                    mutable.setZ(pos.getZ()+j);
                    if (Distance.euclidean.apply(i, j) <= r) {
                        if (place) {
                            world.setBlockState(mutable, state, flags);
                        }else {
                            world.breakBlock(mutable, true);
                        }
                    }
                }
            }
        }
    }

    static void renderRoot(WorldAccess world, TreeNode node, BlockPos pos, BlockState state){
        BlockPos.Mutable mutable = pos.mutableCopy();
        int r = MathUtil.floor(node.volume);
        if (r > 5){r = 5;}
        if (r != 0){
            for (int i = -r; i <= r; i++) {
                for (int j = -r; j <= r; j++) {
                    for (int k = -5; k < 0; k++) {
                        mutable.setX(pos.getX() + i);
                        mutable.setY(pos.getY() + k);
                        mutable.setZ(pos.getZ() + j);
                        if (world.isAir(mutable)) {
                            if (Distance.euclidean.apply(i, j) <= r) {
                                world.setBlockState(mutable, state, flags);
                            }
                        }
                    }
                    if (i == 0 && j == 0){continue;}
                    mutable.setX(pos.getX() + i);
                    mutable.setY(pos.getY());
                    mutable.setZ(pos.getZ() + j);
                    if (Distance.euclidean.apply(i, j) <= r) {
                        world.setBlockState(mutable, state, flags);
                    }
                }
            }
        }
    }

}
