package com.wildsregrown.blocks.render;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.BlockState;

public interface ITintedBlock {
    default int getTint(BlockState state, int tintIndex) {
        return state.get(ModProperties.LINSEED_PAINT).getRGB();
    };
}
