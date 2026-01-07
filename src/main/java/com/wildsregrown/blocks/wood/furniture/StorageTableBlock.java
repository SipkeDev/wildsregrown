package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.HorizontalConnectingBlock;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.GenericSingleStorageEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StorageTableBlock extends HorizontalConnectingBlock implements ITintedBlock, BlockEntityProvider {

    private static final VoxelShape single;
    private static final VoxelShape[] middle;
    private static final VoxelShape[] left;
    private static final VoxelShape[] right;
    private static final EnumProperty<HorizontalConnected> SHAPE = ModProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public StorageTableBlock(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(SHAPE, HorizontalConnected.SINGLE).with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof GenericSingleStorageEntity entity){
            ItemStack entityStack = entity.getStack();
            entity.setStack(player.getMainHandStack());
            player.setStackInHand(Hand.MAIN_HAND, entityStack);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSingleStorageEntity(pos, state);
    }

    @Override
    public boolean isConnectingBlock(BlockState state) {
        if (state.getBlock() instanceof StorageTableBlock){
            return true;
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, SHAPE, FACING, Properties.WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.HORIZONTAL_FACING)){
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
                return VoxelShapes.fullCube();
            }
        }
    }

    private static VoxelShape getShape(BlockState state, int i){
        switch (state.get(SHAPE)){
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
        return VoxelShapes.fullCube();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    static {
        single = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.875, 0.25, 0.875),
                VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.25, 0.875, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.9375, 0.875, 0.9375),
                VoxelShapes.cuboid(0.75, 0, 0.0625, 0.9375, 0.875, 0.25),
                VoxelShapes.cuboid(0.0625, 0, 0.75, 0.25, 0.875, 0.9375)
        );

        middle = new VoxelShape[4];
        middle[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.0625, 0.125, 0.125, 0.9375, 0.25, 0.875),
                VoxelShapes.cuboid(0.9375, 0, 0.0625, 1, 0.875, 0.9375),
                VoxelShapes.cuboid(0, 0, 0.0625, 0.0625, 0.875, 0.9375)
        );
        middle[1] = VoxelTransform.rotate90(middle[0]);
        middle[2] = VoxelTransform.rotate180(middle[0]);
        middle[3] = VoxelTransform.rotate270(middle[0]);

        left = new VoxelShape[4];
        left[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.25, 0.125, 0.125, 0.9375, 0.25, 0.875),
                VoxelShapes.cuboid(0.0625, 0, 0.375, 0.125, 0.875, 0.625),
                VoxelShapes.cuboid(0.125, 0, 0.0625, 0.25, 0.875, 0.9375),
                VoxelShapes.cuboid(0.9375, 0, 0.0625, 1, 0.875, 0.9375)
        );
        left[1] = VoxelTransform.rotate90(left[0]);
        left[2] = VoxelTransform.rotate180(left[0]);
        left[3] = VoxelTransform.rotate270(left[0]);

        right = new VoxelShape[4];
        right[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.0625, 0.125, 0.125, 0.75, 0.25, 0.875),
                VoxelShapes.cuboid(0.875, 0, 0.375, 0.9375, 0.875, 0.625),
                VoxelShapes.cuboid(0.75, 0, 0.0625, 0.875, 0.875, 0.9375)
        );
        right[1] = VoxelTransform.rotate90(right[0]);
        right[2] = VoxelTransform.rotate180(right[0]);
        right[3] = VoxelTransform.rotate270(right[0]);
    }

}
