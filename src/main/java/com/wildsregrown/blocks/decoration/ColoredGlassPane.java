package com.wildsregrown.blocks.decoration;

import com.wildsregrown.blocks.render.IRenderType;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.BlockState;

public class ColoredGlassPane extends GlassPane implements ITintedBlock, IRenderType {

    private final int rgb;

    public ColoredGlassPane(Settings settings, int rgb) {
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
