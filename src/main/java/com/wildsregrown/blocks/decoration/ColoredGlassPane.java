package com.wildsregrown.blocks.decoration;

import net.minecraft.world.level.block.state.BlockState;
import wildsregrown.api.block.render.IRenderType;
import wildsregrown.api.block.render.ITintedBlock;

public class ColoredGlassPane extends GlassPane implements ITintedBlock, IRenderType {

    private final int rgb;

    public ColoredGlassPane(Properties settings, int rgb) {
        super(settings);
        this.rgb = rgb;
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return rgb;
    };

    @Override
    public int getRenderType(){
        return 2;
    }

}
