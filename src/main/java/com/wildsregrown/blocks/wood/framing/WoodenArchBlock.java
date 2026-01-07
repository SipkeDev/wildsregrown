package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.stone.castle.ArchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class WoodenArchBlock extends ArchBlock implements Waterloggable, ITintedBlock {

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public WoodenArchBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(Properties.HORIZONTAL_AXIS, Direction.Axis.X).with(Properties.WATERLOGGED, false).with(Properties.BLOCK_HALF, BlockHalf.TOP));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean waterlogged = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        BlockState state = getDefaultState().with(Properties.WATERLOGGED, waterlogged);
        Vec3d centerPos = ctx.getBlockPos().toCenterPos();
        Vec3d offset = centerPos.subtract(ctx.getHitPos());

        if (offset.y < 0.12f) {
            state = state.with(Properties.BLOCK_HALF, BlockHalf.BOTTOM);
        }

        return state.with(Properties.HORIZONTAL_AXIS, ctx.getHorizontalPlayerFacing().getAxis());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, Properties.HORIZONTAL_AXIS, Properties.WATERLOGGED, Properties.BLOCK_HALF);
    }
}