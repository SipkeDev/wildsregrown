
package com.wildsregrown.entity.blockEntities.renderstates;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class TableChestRenderState extends BlockEntityRenderState {

    public int light = 255;
    public final ItemStackRenderState[] state = new ItemStackRenderState[3];
    public Direction facing = null;
    public boolean open = false;

    public TableChestRenderState init(){
        for (int i = 0; i < 3; i++) {
            state[i] = new ItemStackRenderState();
        }
        return this;
    }

}