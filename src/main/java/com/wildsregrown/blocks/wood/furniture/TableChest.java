package com.wildsregrown.blocks.wood.furniture;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.TableChestEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TableChest extends BlockWithEntity implements BlockEntityProvider, ITintedBlock {

    private static MapCodec<TableChest> CODEC;
    private static final VoxelShape[] SHAPE;
    private static final BooleanProperty OPEN = Properties.OPEN;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public TableChest(Settings settings) {
        super(settings);
        CODEC = createCodec(TableChest::new);
        setDefaultState(getDefaultState().with(OPEN, Boolean.FALSE).with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    /**
     * Interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        Vec3d offset = pos.toCenterPos().subtract(hit.getPos());

        boolean center, slotL, slotR;
        if (state.get(Properties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z){
            center = Math.abs(offset.x) < 0.1;
            slotL = offset.x > -0.3 && offset.x < 0;
            slotR = offset.x < 0.3 && offset.x > 0;
        }else {
            center = Math.abs(offset.z) < 0.1;
            slotL = offset.z > -0.3 && offset.z < 0;
            slotR = offset.z < 0.3 && offset.z > 0;
        }

        if (world.getBlockEntity(pos) instanceof TableChestEntity entity) {
            if (center) {
                interactWithSlot(entity, player, 1, world, pos, state);
            } else if (slotR) {
                interactWithSlot(entity, player, 0, world, pos, state);
            } else if (slotL) {
                interactWithSlot(entity, player, 2, world, pos, state);
            } else {
                world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)));
            }
        }

        return ActionResult.PASS;
    }

    private ActionResult interactWithSlot(TableChestEntity entity, PlayerEntity player, int i, World world, BlockPos pos, BlockState state) {

        if (entity.getCachedState().get(Properties.OPEN)) {
            if (entity.getStack(i).isEmpty() && !player.getMainHandStack().isEmpty()) {
                entity.setStack(i, player.getMainHandStack());
                player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                handleInteraction(entity, world, pos, state);
                return ActionResult.SUCCESS;
            } else if (!entity.getStack(i).isEmpty() && player.getMainHandStack().isEmpty()) {
                player.setStackInHand(Hand.MAIN_HAND, entity.getStack(i));
                entity.setStack(i, ItemStack.EMPTY);
                handleInteraction(entity, world, pos, state);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private void handleInteraction(TableChestEntity entity, World world, BlockPos pos, BlockState state) {
        entity.markDirty();
        world.updateListeners(pos, state, state, 0);
        displayInteraction(world, pos);
    }

    private void displayInteraction(World world, BlockPos pos){
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    /**
     * Entity
     */

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TableChestEntity(pos, state);
    }

    /**
     * VanillaConstructors
     */

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()){
            case X -> {
                return SHAPE[1];
            }
            case Z -> {
                return SHAPE[0];
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, FACING, PAINT);
    }

    static {
        SHAPE = new VoxelShape[2];
        SHAPE[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0.0625, 0.3125, 0.8125, 0.125, 0.6875),
                VoxelShapes.cuboid(0.1875, 0.125, 0.625, 0.8125, 0.25, 0.6875),
                VoxelShapes.cuboid(0.1875, 0.125, 0.3125, 0.8125, 0.25, 0.375),
                VoxelShapes.cuboid(0.125, 0, 0.3125, 0.1875, 0.25, 0.6875),
                VoxelShapes.cuboid(0.8125, 0, 0.3125, 0.875, 0.25, 0.6875)
        );
        SHAPE[1] = VoxelTransform.rotate90(SHAPE[0]);
    }

}
