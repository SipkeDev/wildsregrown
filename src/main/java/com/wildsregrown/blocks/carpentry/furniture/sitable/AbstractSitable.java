package com.wildsregrown.blocks.carpentry.furniture.sitable;

import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AbstractSitable extends Block {

    protected SitEntity sitAbleEntity;
    private final double height;

    public AbstractSitable(Properties settings, double height){
        super(settings);
        this.height = height;
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
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        if (sitAbleEntity != null) {
            sitAbleEntity.discard();
        }
    }

}
