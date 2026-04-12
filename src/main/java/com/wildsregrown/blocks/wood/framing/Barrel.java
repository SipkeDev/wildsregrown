package com.wildsregrown.blocks.wood.framing;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.wood.furniture.kitchen.counter.CounterChestBlock;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Barrel extends BlockWithEntity implements ITintedBlock {

    private static final VoxelShape[] shapes;
    private static final EnumProperty<Direction> FACING = Properties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty OPEN = Properties.OPEN;

    public Barrel(Settings settings){
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.UP).with(OPEN, false).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction dir = ctx.getPlayerLookDirection();
        return getDefaultState().with(FACING, dir.getOpposite());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Barrel");
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof GenericSmallStorageEntity entity) {
            if (player.isSneaking()){
                world.setBlockState(pos, state, 3);
                handleInteraction(entity, world, pos, state);
            }else {
                if (!world.isClient()) {
                    player.openHandledScreen(entity);
                }
                handleInteraction(entity, world, pos, state);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;

    }

    private void handleInteraction(GenericSmallStorageEntity entity, World world, BlockPos pos, BlockState state) {
        entity.markDirty();
        world.updateListeners(pos, state, state, 3);
        displayInteraction(world, pos);
    }

    private void displayInteraction(World world, BlockPos pos){
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof GenericSmallStorageEntity entity){
            entity.updateViewerCount(world, pos, state);
        }
    }

    /**
     * Vanilla constructors
     * @return
     */

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(CounterChestBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, OPEN);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.AXIS, rotation.rotate(state.get(Properties.AXIS).getPositiveDirection()).getAxis());
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.AXIS, mirror.apply(state.get(Properties.AXIS).getPositiveDirection()).getAxis());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()){
            case X -> {
                return shapes[0];
            }
            case Y -> {
                return shapes[1];
            }
            case Z -> {
                return shapes[2];
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    static {
        shapes = new VoxelShape[3];
        shapes[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );
        shapes[1] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );;
        shapes[2] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );;
    }

}
