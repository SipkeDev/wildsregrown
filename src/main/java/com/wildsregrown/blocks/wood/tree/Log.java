package com.wildsregrown.blocks.wood.tree;

import com.sipke.api.features.trees.type.ITreeType;
import com.sipke.api.features.trees.type.TreeType;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class Log extends PillarBlock implements ITreeType, ITintedBlock {

    private static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public Log(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(AXIS, Direction.Axis.Y));
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        /*
        BlockPos.Mutable loc;

        if (state.get(AXIS).isVertical()) {
            for (OrdinalDirection d : OrdinalDirection.values()) {
                loc = pos.mutableCopy().move(0, 1, 0).move(d.getVector());
                for (int i = 0; i < 3; i++) {
                    BlockState branch = world.getBlockState(loc);
                    if (branch.isIn(BlockTags.LEAVES) || (branch.getBlock() instanceof TreeBranch && TreeBranch.toBreak(branch, loc, pos))) {
                        world.breakBlock(loc, true);
                    }
                    loc = loc.move(0, -1, 0);
                }
            }
        }
         */
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, PAINT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return tintIndex == 1 ? state.get(PAINT).getRGB() : -1;
    }

    @Override
    public TreeType getType() {
        return TreeType.trunk;
    }
}
