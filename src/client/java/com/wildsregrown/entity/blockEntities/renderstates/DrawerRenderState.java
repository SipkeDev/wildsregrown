package com.wildsregrown.entity.blockEntities.renderstates;

import com.wildsregrown.blocks.properties.DrawerState;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

public class DrawerRenderState extends BlockEntityRenderState {
    public int light = 255;
    public final ItemRenderState[] state = new ItemRenderState[8];
    public boolean hasLid = false;
    public Direction facing;
    public DrawerState drawerState;

    public DrawerRenderState init(){
        for (int i = 0; i < 8; i++) {
            state[i] = new ItemRenderState();
        }
        return this;
    }
}