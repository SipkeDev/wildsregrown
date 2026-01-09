package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public class SingleItemRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemRenderState renderState = new ItemRenderState();
}