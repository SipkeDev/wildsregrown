
package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.Door;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public class WoodenDoor extends Door implements Waterloggable, ITintedBlock {

    public WoodenDoor(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(ModProperties.LINSEED_PAINT, Properties.OPEN, PART, Properties.HORIZONTAL_FACING, Properties.WATERLOGGED);
    }

}