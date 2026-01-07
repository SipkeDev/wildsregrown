package com.wildsregrown.blocks.flora.grass;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Has a typical european grassfield color palette
 */
public class TallGrass extends Grass {

    public TallGrass(Settings settings, int rgb) {
        super(settings.velocityMultiplier(0.5f), rgb, 0.8f, 3, 6);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

}