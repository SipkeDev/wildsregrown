package com.wildsregrown.entity.render.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class CrateRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemRenderState renderState = new ItemRenderState();
    public boolean hasLid = false;
    public int count;
}