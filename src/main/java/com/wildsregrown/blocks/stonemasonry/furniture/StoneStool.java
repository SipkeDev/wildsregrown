package com.wildsregrown.blocks.stonemasonry.furniture;

import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StoneStool extends Block {

    private static final VoxelShape shape = Shapes.or(
            Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.375, 0.3125),
            Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.375, 0.3125),
            Shapes.box(0.6875, 0, 0.6875, 0.8125, 0.375, 0.8125),
            Shapes.box(0.1875, 0, 0.6875, 0.3125, 0.375, 0.8125),
            Shapes.box(0.125, 0.375, 0.125, 0.875, 0.5, 0.875)
    );;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected SitEntity sitAbleEntity;
    private final float height;

    public StoneStool(Properties settings){
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
        this.height = 0.35f;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (player.isSpectator() || player.isShiftKeyDown()) {
            return InteractionResult.FAIL;
        }

        List<SitEntity> active = world.getEntitiesOfClass(SitEntity.class, new AABB(pos), Entity::isVehicle);
        List<Entity> hasPassenger = new ArrayList<>();
        active.forEach(chairEntity -> hasPassenger.add(chairEntity.getFirstPassenger()));

        if (!active.isEmpty() && hasPassenger.stream().anyMatch(Entity::isAlwaysTicking)) {
            return InteractionResult.FAIL;
        } else if (!active.isEmpty()) {
            hasPassenger.forEach(Entity::stopRiding);
            return InteractionResult.SUCCESS;
        } else if (sitEntity(world, pos, state, player) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

    protected InteractionResult sitEntity(Level world, BlockPos pos, BlockState state, Entity entityToSit) {

        double posX = pos.getX() + 0.5;
        double posY = pos.getY() + this.height;
        double posZ = pos.getZ() + 0.5;

        float yaw = entityToSit.getYRot();
        this.sitAbleEntity = ModEntities.sitAbleEntity.create(world, EntitySpawnReason.DISPENSER);
        sitAbleEntity.snapTo(posX, posY, posZ, yaw, 0);
        sitAbleEntity.setNoGravity(true);
        sitAbleEntity.setSilent(true);
        sitAbleEntity.setInvisible(false);
        sitAbleEntity.setInvulnerable(true);

        if (world.addFreshEntity(sitAbleEntity)) {
            entityToSit.setYRot(yaw);
            entityToSit.setYBodyRot(yaw);
            entityToSit.setYHeadRot(yaw);
            entityToSit.startRiding(sitAbleEntity, true, true);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        if (sitAbleEntity != null) {
            sitAbleEntity.discard();
        }
    }
}
