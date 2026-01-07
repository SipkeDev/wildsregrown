package com.wildsregrown.blocks.wood.furniture;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.CounterShelvesEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
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

public class CounterShelvesBlock extends BlockWithEntity implements ITintedBlock {

    private static final VoxelShape[] shapes;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public CounterShelvesBlock(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE));
    }

    /**
     * Entity
     */
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CounterShelvesEntity(pos, state);
    }

    /**
     * Interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        Vec3d offset = pos.toCenterPos().subtract(hit.getPos());

        WildsRegrown.LOGGER.info("offset" + offset);

        boolean top = offset.y > 0;
        boolean slot = state.get(Properties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ?offset.x > 0 : offset.z > 0;

        if (world.getBlockEntity(pos) instanceof CounterShelvesEntity entity) {
            if (top) {
                if (slot){
                    return interactWithSlot(entity, player, 0, world, pos, state);
                }else {
                    return interactWithSlot(entity, player, 1, world, pos, state);
                }
            }else {
                if (slot){
                    return interactWithSlot(entity, player, 2, world, pos, state);
                }else {
                    return interactWithSlot(entity, player, 3, world, pos, state);
                }
            }
        }

        return ActionResult.PASS;
    }

    private ActionResult interactWithSlot(CounterShelvesEntity entity, PlayerEntity player, int i, World world, BlockPos pos, BlockState state) {
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
        } else {
            return ActionResult.PASS;
        }
    }

    private void handleInteraction(CounterShelvesEntity entity, World world, BlockPos pos, BlockState state) {
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
     * Vanilla constructors
     * @return
     */

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(CounterShelvesBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING);
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
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
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
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    static {
        shapes = new VoxelShape[4];
        shapes[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.875, 0, 0.25, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0, 0.25, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.4375, 0.25, 0.875, 0.5625, 1),
                VoxelShapes.cuboid(0.125, 0, 0.25, 0.875, 0.125, 1)
        );
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }

}
