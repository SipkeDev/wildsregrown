package com.wildsregrown.blocks.wood.furniture;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.ShelvesEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShelvesBlock extends BlockWithEntity implements ITintedBlock {

    private static final VoxelShape[] SHAPE;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public ShelvesBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ShelvesBlock::new);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        Vec3d offset = pos.toCenterPos().subtract(hit.getPos());

        boolean slot = state.get(Properties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ?offset.x > 0 : offset.z > 0;

        if (world.getBlockEntity(pos) instanceof ShelvesEntity entity) {
            if (slot) {
                entity.swapStack(0, player);
            } else {
                entity.swapStack(1, player);
            }
            handleInteraction(entity, world, pos, state);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private void handleInteraction(ShelvesEntity entity, World world, BlockPos pos, BlockState state) {
        entity.markDirty();
        world.updateListeners(pos, state, state, 0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {return SHAPE[0];}
            case EAST -> {return SHAPE[1];}
            case SOUTH -> {return SHAPE[2];}
            case WEST -> {return SHAPE[3];}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        SHAPE = new VoxelShape[4];
        SHAPE[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.25, 0.5, 1, 0.375, 1),
                VoxelShapes.cuboid(0.875, 0.125, 0.6875, 1, 0.25, 0.8125),
                VoxelShapes.cuboid(0.875, 0, 0.8125, 1, 0.25, 1),
                VoxelShapes.cuboid(0, 0, 0.8125, 0.125, 0.25, 1),
                VoxelShapes.cuboid(0, 0.125, 0.6875, 0.125, 0.25, 0.8125)
        );
        SHAPE[1] = VoxelTransform.rotate90(SHAPE[0]);
        SHAPE[2] = VoxelTransform.rotate180(SHAPE[0]);
        SHAPE[3] = VoxelTransform.rotate270(SHAPE[0]);

    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelvesEntity(pos, state);
    }
}
