package com.wildsregrown.blocks.flora;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import wildsregrown.api.block.flora.type.GrassFlora;

/**
 * Has a typical european grassfield color palette
 */
public class TallGrass extends GrassFlora {

    public TallGrass(Properties settings, int rgb) {
        super(settings.speedFactor(0.5f), rgb, 0.8f, 3, 6);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

}