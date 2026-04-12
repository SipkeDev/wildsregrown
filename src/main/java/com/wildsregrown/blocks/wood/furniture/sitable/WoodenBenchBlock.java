package com.wildsregrown.blocks.wood.furniture.sitable;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.HorizontalConnectingBlock;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.SitEntity;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WoodenBenchBlock extends HorizontalConnectingBlock implements ITintedBlock {

    private static final VoxelShape[] single;
    private static final VoxelShape[] middle;
    private static final VoxelShape[] left;
    private static final VoxelShape[] right;
    private static final EnumProperty<HorizontalConnected> SHAPE = ModProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final IntProperty VAR = ModProperties.VARIATIONS_2;
    protected SitEntity sitAbleEntity;
    private final float height;

    public WoodenBenchBlock(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(SHAPE, HorizontalConnected.SINGLE).with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
        this.height = 0.5f;
    }

    @Override
    public boolean isConnectingBlock(BlockState neighbour, BlockState current) {
        if (neighbour.getBlock() instanceof WoodenBenchBlock){
            if (current.getBlock() instanceof WoodenBenchBlock) {
                return Objects.equals(neighbour.get(VAR), current.get(VAR));
            }
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, SHAPE, VAR, FACING, Properties.WATERLOGGED);
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
        i += ((state.get(VAR)-1)*4);
        switch (state.get(SHAPE)){
            case SINGLE -> {
                return single[i];
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

    static {
        single = new VoxelShape[8];
        single[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                VoxelShapes.cuboid(0.0625, 0.1875, 0.40625, 0.125, 0.3125, 0.59375),
                VoxelShapes.cuboid(0.0625, 0.375, 0.3125, 0.125, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.0625, 0.0625, 0.3125, 0.125, 0.125, 0.6875),
                VoxelShapes.cuboid(0.0625, 0.125, 0.375, 0.125, 0.1875, 0.625),
                VoxelShapes.cuboid(0.0625, 0.3125, 0.375, 0.125, 0.375, 0.625),
                VoxelShapes.cuboid(0.03125, 0, 0.3125, 0.15625, 0.0625, 0.4375),
                VoxelShapes.cuboid(0.03125, 0, 0.5625, 0.15625, 0.0625, 0.6875),
                VoxelShapes.cuboid(0.84375, 0, 0.5625, 0.96875, 0.0625, 0.6875),
                VoxelShapes.cuboid(0.875, 0.1875, 0.40625, 0.9375, 0.3125, 0.59375),
                VoxelShapes.cuboid(0.875, 0.375, 0.3125, 0.9375, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.875, 0.0625, 0.3125, 0.9375, 0.125, 0.6875),
                VoxelShapes.cuboid(0.875, 0.125, 0.375, 0.9375, 0.1875, 0.625),
                VoxelShapes.cuboid(0.875, 0.3125, 0.375, 0.9375, 0.375, 0.625),
                VoxelShapes.cuboid(0.84375, 0, 0.3125, 0.96875, 0.0625, 0.4375),
                VoxelShapes.cuboid(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        single[1] = VoxelTransform.rotate90(single[0]);
        single[2] = VoxelTransform.rotate180(single[0]);
        single[3] = VoxelTransform.rotate270(single[0]);
        single[4] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.25, 0.0625, 0.4375, 0.75),
                VoxelShapes.cuboid(0.9375, 0, 0.25, 1, 0.4375, 0.75),
                VoxelShapes.cuboid(0, 0.4375, 0.25, 1, 0.5, 0.75)
        );
        single[5] = VoxelTransform.rotate90(single[4]);
        single[6] = VoxelTransform.rotate180(single[4]);
        single[7] = VoxelTransform.rotate270(single[4]);

        middle = new VoxelShape[8];
        middle[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                VoxelShapes.cuboid(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        middle[1] = VoxelTransform.rotate90(middle[0]);
        middle[2] = VoxelTransform.rotate180(middle[0]);
        middle[3] = VoxelTransform.rotate270(middle[0]);
        middle[4] = VoxelShapes.cuboid(0, 0.4375, 0.25, 1, 0.5, 0.75);
        middle[5] = VoxelTransform.rotate90(middle[4]);
        middle[6] = VoxelTransform.rotate180(middle[4]);
        middle[7] = VoxelTransform.rotate270(middle[4]);

        left = new VoxelShape[8];
        left[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.4375, 0.1875, 1, 0.5, 0.8125),
                VoxelShapes.cuboid(0.0625, 0.1875, 0.40625, 0.125, 0.3125, 0.59375),
                VoxelShapes.cuboid(0.0625, 0.375, 0.3125, 0.125, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.0625, 0.0625, 0.3125, 0.125, 0.125, 0.6875),
                VoxelShapes.cuboid(0.0625, 0.125, 0.375, 0.125, 0.1875, 0.625),
                VoxelShapes.cuboid(0.0625, 0.3125, 0.375, 0.125, 0.375, 0.625),
                VoxelShapes.cuboid(0.03125, 0, 0.3125, 0.15625, 0.0625, 0.4375),
                VoxelShapes.cuboid(0.03125, 0, 0.5625, 0.15625, 0.0625, 0.6875),
                VoxelShapes.cuboid(0, 0.21875, 0.46875, 1, 0.28125, 0.53125)
        );
        left[1] = VoxelTransform.rotate90(left[0]);
        left[2] = VoxelTransform.rotate180(left[0]);
        left[3] = VoxelTransform.rotate270(left[0]);
        left[4] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.25, 0.0625, 0.4375, 0.75),
                VoxelShapes.cuboid(0, 0.4375, 0.25, 1, 0.5, 0.75)
        );
        left[5] = VoxelTransform.rotate90(left[4]);
        left[6] = VoxelTransform.rotate180(left[4]);
        left[7] = VoxelTransform.rotate270(left[4]);

        right = new VoxelShape[8];
        right[0] = VoxelTransform.mirrorX(left[0]);
        right[1] = VoxelTransform.rotate90(right[0]);
        right[2] = VoxelTransform.rotate180(right[0]);
        right[3] = VoxelTransform.rotate270(right[0]);
        right[4] = VoxelTransform.mirrorX(left[4]);
        right[5] = VoxelTransform.rotate90(right[4]);
        right[6] = VoxelTransform.rotate180(right[4]);
        right[7] = VoxelTransform.rotate270(right[4]);
    }

}
