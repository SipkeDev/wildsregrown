package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class CounterShelvesRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemStackRenderState[] state = new ItemStackRenderState[4];
    public Direction facing = null;

    public CounterShelvesRenderState init(){
        for (int i = 0; i < 4; i++) {
            state[i] = new ItemStackRenderState();
        }
        return this;
    }

}