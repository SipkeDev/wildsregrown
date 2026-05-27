package com.wildsregrown.blocks.carpentry.furniture.storage;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class NightStandBlock extends BaseEntityBlock implements ITintedBlock {

    private static final VoxelShape[] shapes;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public NightStandBlock(Properties settings){
        super(settings.randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Night Stand");
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {
                return shapes[0];
            }
            case EAST -> {
                return shapes[1];
            }
            case SOUTH -> {
                return shapes[2];
            }
            case WEST -> {
                return shapes[3];
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(NightStandBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, OPEN);
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
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        shapes = new VoxelShape[4];
        shapes[0] = Shapes.or(
                Shapes.box(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                Shapes.box(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                Shapes.box(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }

}
