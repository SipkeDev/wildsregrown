package com.wildsregrown.entity.render.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class ItemLootPedestalRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemRenderState renderState = new ItemRenderState();
    public Direction facing = null;
    public int var;
}