package com.wildsregrown.blocks.carpentry.furniture.storage;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.GenericSingleStorageEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.abstracts.HorizontalConnectingBlock;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.render.ITintedBlock;

public class StorageTableBlock extends HorizontalConnectingBlock implements ITintedBlock, EntityBlock {

    private static final VoxelShape single;
    private static final VoxelShape[] middle;
    private static final VoxelShape[] left;
    private static final VoxelShape[] right;
    private static final EnumProperty<HorizontalConnected> SHAPE = WRGProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public StorageTableBlock(Properties settings){
        super(settings);
        //this.registerDefaultState(defaultBlockState().setValue(SHAPE, HorizontalConnected.SINGLE).setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof GenericSingleStorageEntity entity){
            ItemStack entityStack = entity.getStack();
            entity.setStack(player.getMainHandItem());
            player.setItemInHand(InteractionHand.MAIN_HAND, entityStack);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSingleStorageEntity(pos, state);
    }

    @Override
    public boolean isConnectingBlock(BlockState current, BlockState state) {
        return current.getBlock() instanceof StorageTableBlock;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, SHAPE, FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)){
            case NORTH -> {
                return getShape(state, 0);
            }
            case EAST -> {
                return getShape(state, 1);
            }
            case SOUTH -> {
                return getShape(state, 2);
            }
            case WEST -> {
                return getShape(state, 3);
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    private static VoxelShape getShape(BlockState state, int i){
        switch (state.getValue(SHAPE)){
            case SINGLE -> {
                return single;
            }
            case MIDDLE -> {
                return middle[i];
            }
            case LEFT -> {
                return left[i];
            }
            case RIGHT -> {
                return right[i];
            }
        }
        return Shapes.block();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        single = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.125, 0.125, 0.125, 0.875, 0.25, 0.875),
                Shapes.box(0.0625, 0, 0.0625, 0.25, 0.875, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.9375, 0.875, 0.9375),
                Shapes.box(0.75, 0, 0.0625, 0.9375, 0.875, 0.25),
                Shapes.box(0.0625, 0, 0.75, 0.25, 0.875, 0.9375)
        );

        middle = new VoxelShape[4];
        middle[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.0625, 0.125, 0.125, 0.9375, 0.25, 0.875),
                Shapes.box(0.9375, 0, 0.0625, 1, 0.875, 0.9375),
                Shapes.box(0, 0, 0.0625, 0.0625, 0.875, 0.9375)
        );
        middle[1] = VoxelTransform.rotate90(middle[0]);
        middle[2] = VoxelTransform.rotate180(middle[0]);
        middle[3] = VoxelTransform.rotate270(middle[0]);

        left = new VoxelShape[4];
        left[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.25, 0.125, 0.125, 0.9375, 0.25, 0.875),
                Shapes.box(0.0625, 0, 0.375, 0.125, 0.875, 0.625),
                Shapes.box(0.125, 0, 0.0625, 0.25, 0.875, 0.9375),
                Shapes.box(0.9375, 0, 0.0625, 1, 0.875, 0.9375)
        );
        left[1] = VoxelTransform.rotate90(left[0]);
        left[2] = VoxelTransform.rotate180(left[0]);
        left[3] = VoxelTransform.rotate270(left[0]);

        right = new VoxelShape[4];
        right[0] = Shapes.or(
                Shapes.box(0, 0.875, 0, 1, 1, 1),
                Shapes.box(0.0625, 0.125, 0.125, 0.75, 0.25, 0.875),
                Shapes.box(0.875, 0, 0.375, 0.9375, 0.875, 0.625),
                Shapes.box(0.75, 0, 0.0625, 0.875, 0.875, 0.9375)
        );
        right[1] = VoxelTransform.rotate90(right[0]);
        right[2] = VoxelTransform.rotate180(right[0]);
        right[3] = VoxelTransform.rotate270(right[0]);
    }

}
