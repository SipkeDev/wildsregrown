package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.stonemasonry.castle.ArchBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenArchBlock extends ArchBlock implements SimpleWaterloggedBlock, ITintedBlock {

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public WoodenArchBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X).setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.HALF, Half.TOP));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        BlockState state = defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged);
        Vec3 centerPos = ctx.getClickedPos().getCenter();
        Vec3 offset = centerPos.subtract(ctx.getClickLocation());

        if (offset.y < 0.12f) {
            state = state.setValue(BlockStateProperties.HALF, Half.BOTTOM);
        }

        return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, ctx.getHorizontalDirection().getAxis());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.WATERLOGGED, BlockStateProperties.HALF);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

}