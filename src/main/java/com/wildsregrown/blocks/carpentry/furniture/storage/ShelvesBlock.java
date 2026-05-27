package com.wildsregrown.blocks.carpentry.furniture.storage;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.ShelvesEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class ShelvesBlock extends BaseEntityBlock implements ITintedBlock {

    private static final VoxelShape[] SHAPE;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public ShelvesBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ShelvesBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        Vec3 offset = pos.getCenter().subtract(hit.getLocation());

        boolean slot = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ?offset.x > 0 : offset.z > 0;

        if (world.getBlockEntity(pos) instanceof ShelvesEntity entity) {
            if (slot) {
                entity.swapStack(0, player);
            } else {
                entity.swapStack(1, player);
            }
            handleInteraction(entity, world, pos, state);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void handleInteraction(ShelvesEntity entity, Level world, BlockPos pos, BlockState state) {
        entity.setChanged();
        world.sendBlockUpdated(pos, state, state, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {return SHAPE[0];}
            case EAST -> {return SHAPE[1];}
            case SOUTH -> {return SHAPE[2];}
            case WEST -> {return SHAPE[3];}
            default -> {return Shapes.block();}
        }
    }

    static {
        SHAPE = new VoxelShape[4];
        SHAPE[0] = Shapes.or(
                Shapes.box(0, 0.25, 0.5, 1, 0.375, 1),
                Shapes.box(0.875, 0.125, 0.6875, 1, 0.25, 0.8125),
                Shapes.box(0.875, 0, 0.8125, 1, 0.25, 1),
                Shapes.box(0, 0, 0.8125, 0.125, 0.25, 1),
                Shapes.box(0, 0.125, 0.6875, 0.125, 0.25, 0.8125)
        );
        SHAPE[1] = VoxelTransform.rotate90(SHAPE[0]);
        SHAPE[2] = VoxelTransform.rotate180(SHAPE[0]);
        SHAPE[3] = VoxelTransform.rotate270(SHAPE[0]);

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShelvesEntity(pos, state);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }
}
