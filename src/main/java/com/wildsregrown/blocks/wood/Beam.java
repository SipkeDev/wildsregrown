package com.wildsregrown.blocks.wood;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.Quadrant;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.stone.Edge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class Beam extends Edge implements ITintedBlock {

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    private static final EnumProperty<Quadrant> QUADRANT = Quadrant.QUADRANT;

    public Beam(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, QUADRANT, AXIS, Properties.WATERLOGGED);
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return tintIndex == 1 ? state.get(PAINT).getRGB() : -1;
    }

}
