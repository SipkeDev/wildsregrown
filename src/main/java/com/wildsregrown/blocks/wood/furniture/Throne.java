package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;

public class Throne extends Chair implements ITintedBlock {

    private static final VoxelShape[] shapes;

    public Throne(Settings settings, float height){
        super(settings, height);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.HORIZONTAL_FACING)){
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
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return getDefaultState()
                .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }


    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.CONSUME;
        }

        if (player.isSpectator() || player.isSneaking()) {
            return ActionResult.FAIL;
        }

        List<SitEntity> active = world.getEntitiesByClass(SitEntity.class, new Box(pos), Entity::hasPassengers);
        List<Entity> hasPassenger = new ArrayList<>();
        active.forEach(chairEntity -> hasPassenger.add(chairEntity.getFirstPassenger()));

        if (!active.isEmpty() && hasPassenger.stream().anyMatch(Entity::isPlayer)) {
            return ActionResult.FAIL;
        } else if (!active.isEmpty()) {
            hasPassenger.forEach(Entity::stopRiding);
            return ActionResult.SUCCESS;
        } else if (sitEntity(world, pos, state, player) == ActionResult.SUCCESS) {
            return ActionResult.SUCCESS;
        }

        return ActionResult.CONSUME;
    }

    protected ActionResult sitEntity(World world, BlockPos pos, BlockState state, Entity entityToSit) {

        double posX = pos.getX() + 0.5;
        double posY = pos.getY() + this.height;
        double posZ = pos.getZ() + 0.5;

        float yaw = entityToSit.getYaw();
        this.sitAbleEntity = ModEntities.sitAbleEntity.create(world, SpawnReason.DISPENSER);
        sitAbleEntity.refreshPositionAndAngles(posX, posY, posZ, yaw, 0);
        sitAbleEntity.setNoGravity(true);
        sitAbleEntity.setSilent(true);
        sitAbleEntity.setInvisible(false);
        sitAbleEntity.setInvulnerable(true);

        if (world.spawnEntity(sitAbleEntity)) {
            entityToSit.setYaw(yaw);
            entityToSit.setBodyYaw(yaw);
            entityToSit.setHeadYaw(yaw);
            entityToSit.startRiding(sitAbleEntity, true, true);

            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (sitAbleEntity != null) {
            sitAbleEntity.discard();
        }
    }

    static {
        shapes = new VoxelShape[4];
        shapes[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 0.125, 0.8125, 1),
                VoxelShapes.cuboid(0.875, 0, 0, 1, 0.8125, 1),
                VoxelShapes.cuboid(0.125, 0, 0.0625, 0.875, 0.5, 1),
                VoxelShapes.cuboid(-0.03125, 0.78125, -0.03125, 0.15625, 0.84375, 0.96875),
                VoxelShapes.cuboid(0.84375, 0.78125, -0.03125, 1.03125, 0.84375, 0.96875),
                VoxelShapes.cuboid(0.875, 0.8125, 0.875, 1, 1.5, 1),
                VoxelShapes.cuboid(0, 0.8125, 0.875, 0.125, 1.5, 1),
                VoxelShapes.cuboid(0.125, 0.5, 0.875, 0.875, 1.5, 1)
        );
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }
}
