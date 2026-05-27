package com.wildsregrown.blocks.carpentry.furniture.sitable;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import wildsregrown.api.block.abstracts.HorizontalConnectingBlock;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.render.ITintedBlock;

public class AbstractSitableBench extends HorizontalConnectingBlock implements ITintedBlock {

    public static final EnumProperty<HorizontalConnected> SHAPE = WRGProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    protected SitEntity sitAbleEntity;
    private final double height;
    private final double offset;

    public AbstractSitableBench(Properties settings, double height, double offset){
        super(settings);
        //this.registerDefaultState(defaultBlockState().setValue(SHAPE, HorizontalConnected.SINGLE).setValue(FACING, Direction.NORTH).setValue(PAINT, LinSeedPaintable.NONE));
        this.height = height;
        this.offset = offset;
    }

    @Override
    public boolean isConnectingBlock(BlockState neighbour, BlockState current) {
        if (neighbour.getBlock() instanceof AbstractSitableBench){
             return(current.getBlock() instanceof AbstractSitableBench);
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, SHAPE, FACING, BlockStateProperties.WATERLOGGED);
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

        if (state.getValue(BlockStateProperties.WATERLOGGED)){
            return InteractionResult.FAIL;
        }

        Direction direction = state.getValue(FACING);
        double posX = pos.getX() + 0.5 + (offset*direction.getStepX());
        double posY = pos.getY() + this.height;
        double posZ = pos.getZ() + 0.5 + (offset*direction.getStepZ());

        float yaw = entityToSit.getYRot();
        this.sitAbleEntity = ModEntities.sitAbleEntity.create(world, EntitySpawnReason.DISPENSER);
        this.sitAbleEntity.snapTo(posX, posY, posZ, yaw, 0);
        this.sitAbleEntity.setNoGravity(true);
        this.sitAbleEntity.setSilent(true);
        this.sitAbleEntity.setInvisible(false);
        this.sitAbleEntity.setInvulnerable(true);

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
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

}
