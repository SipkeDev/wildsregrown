package com.wildsregrown.entity.render.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class StructureBlockRenderState extends BlockEntityRenderState {
    public int light = 255;
    public int x0 = 0;
    public int y0 = 0;
    public int z0 = 0;
    public int x1 = 0;
    public int y1 = 0;
    public int z1 = 0;
    public boolean showOutline = false;
}