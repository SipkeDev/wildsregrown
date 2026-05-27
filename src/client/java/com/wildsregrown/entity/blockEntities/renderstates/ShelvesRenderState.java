package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class ShelvesRenderState extends BlockEntityRenderState {

    public int light = 255;
    public final ItemStackRenderState leftState = new ItemStackRenderState();
    public final ItemStackRenderState rightState = new ItemStackRenderState();
    public Direction facing = null;

}