package com.wildsregrown.blocks.carpentry.furniture;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MirrorBlock extends Block {

    private static final Map<Direction, VoxelShape> basic;
    private static final Map<Direction, VoxelShape> refined;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final int tier;

    public MirrorBlock(Properties settings, int tier){
        super(settings);
        this.tier = tier;
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction dir = state.getValue(FACING);
        return tier == 0 ? basic.get(dir) : refined.get(dir);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    static {
        basic = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0, 0, 0, 1, 1, 0.125)
        ));
        refined = Shapes.rotateHorizontal(Shapes.or(
                Shapes.box(0.3125, 0.0625, 0, 0.6875, 0.8875, 0.125),
                Shapes.box(0.25, 0.0625, 0, 0.3125, 0.9375, 0.1875),
                Shapes.box(0.6875, 0.0625, 0, 0.75, 0.9375, 0.1875),
                Shapes.box(0, 0, 0, 1, 0.0625, 0.1875),
                Shapes.box(0.3125, 0.875, 0, 0.6875, 0.9375, 0.1875),
                Shapes.box(0.0625, 0.625, 0, 0.25, 0.6875, 0.125),
                Shapes.box(0.75, 0.625, 0, 0.9375, 0.6875, 0.125),
                Shapes.box(0, 0.0625, 0, 0.0625, 0.6875, 0.125),
                Shapes.box(0.9375, 0.0625, 0, 1, 0.6875, 0.125),
                Shapes.box(0.0625, 0.0625, 0, 0.25, 0.625, 0.0625),
                Shapes.box(0.75, 0.0625, 0, 0.9375, 0.625, 0.0625)
        ));
    }

}
