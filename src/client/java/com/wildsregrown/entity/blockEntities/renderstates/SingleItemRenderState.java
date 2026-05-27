package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class SingleItemRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemStackRenderState renderState = new ItemStackRenderState();
}