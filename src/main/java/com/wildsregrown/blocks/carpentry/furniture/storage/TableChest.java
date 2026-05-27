package com.wildsregrown.blocks.carpentry.furniture.storage;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.TableChestEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class TableChest extends BaseEntityBlock implements EntityBlock, ITintedBlock {

    private static MapCodec<TableChest> CODEC;
    private static final VoxelShape[] SHAPE;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public TableChest(Properties settings) {
        super(settings);
        CODEC = simpleCodec(TableChest::new);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
    }

    /**
     * Interactions
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        Vec3 offset = pos.getCenter().subtract(hit.getLocation());

        boolean center, slotL, slotR;
        if (state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z){
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
                world.setBlockAndUpdate(pos, state.setValue(OPEN, !state.getValue(OPEN)));
            }
        }

        return InteractionResult.PASS;
    }

    private InteractionResult interactWithSlot(TableChestEntity entity, Player player, int i, Level world, BlockPos pos, BlockState state) {

        if (entity.getBlockState().getValue(BlockStateProperties.OPEN)) {
            if (entity.getItem(i).isEmpty() && !player.getMainHandItem().isEmpty()) {
                entity.setItem(i, player.getMainHandItem());
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                handleInteraction(entity, world, pos, state);
                return InteractionResult.SUCCESS;
            } else if (!entity.getItem(i).isEmpty() && player.getMainHandItem().isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, entity.getItem(i));
                entity.setItem(i, ItemStack.EMPTY);
                handleInteraction(entity, world, pos, state);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void handleInteraction(TableChestEntity entity, Level world, BlockPos pos, BlockState state) {
        entity.setChanged();
        world.sendBlockUpdated(pos, state, state, 0);
        displayInteraction(world, pos);
    }

    private void displayInteraction(Level world, BlockPos pos){
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            serverWorld.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    /**
     * Entity
     */

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableChestEntity(pos, state);
    }

    /**
     * VanillaConstructors
     */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING).getAxis()){
            case X -> {
                return SHAPE[1];
            }
            case Z -> {
                return SHAPE[0];
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN, FACING, PAINT);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        SHAPE = new VoxelShape[2];
        SHAPE[0] = Shapes.or(
                Shapes.box(0.1875, 0.0625, 0.3125, 0.8125, 0.125, 0.6875),
                Shapes.box(0.1875, 0.125, 0.625, 0.8125, 0.25, 0.6875),
                Shapes.box(0.1875, 0.125, 0.3125, 0.8125, 0.25, 0.375),
                Shapes.box(0.125, 0, 0.3125, 0.1875, 0.25, 0.6875),
                Shapes.box(0.8125, 0, 0.3125, 0.875, 0.25, 0.6875)
        );
        SHAPE[1] = VoxelTransform.rotate90(SHAPE[0]);
    }

}
