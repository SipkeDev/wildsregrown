package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class CounterShelvesRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemRenderState[] state = new ItemRenderState[4];
    public Direction facing = null;

    public CounterShelvesRenderState init(){
        for (int i = 0; i < 4; i++) {
            state[i] = new ItemRenderState();
        }
        return this;
    }

}