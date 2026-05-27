package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class ItemLootPedestalRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemStackRenderState renderState = new ItemStackRenderState();
    public Direction facing = null;
    public int var;
}