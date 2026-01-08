
package com.wildsregrown.entity.render.renderstates;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class TableChestRenderState extends BlockEntityRenderState {

    public int light = 255;
    public final ItemRenderState[] state = new ItemRenderState[3];
    public Direction facing = null;
    public boolean open = false;

    public TableChestRenderState init(){
        for (int i = 0; i < 3; i++) {
            state[i] = new ItemRenderState();
        }
        return this;
    }

}