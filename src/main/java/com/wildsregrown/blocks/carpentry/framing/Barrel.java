package com.wildsregrown.blocks.carpentry.framing;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.render.ITintedBlock;

public class Barrel extends BaseEntityBlock implements ITintedBlock {

    private static final VoxelShape[] shapes;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public Barrel(Properties settings){
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP).setValue(OPEN, false).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction dir = ctx.getNearestLookingDirection();
        return defaultBlockState().setValue(FACING, dir.getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Barrel");
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof GenericSmallStorageEntity entity) {
            if (player.isShiftKeyDown()){
                world.setBlock(pos, state, 3);
                handleInteraction(entity, world, pos, state);
            }else {
                if (!world.isClientSide()) {
                    player.openMenu(entity);
                }
                handleInteraction(entity, world, pos, state);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;

    }

    private void handleInteraction(GenericSmallStorageEntity entity, Level world, BlockPos pos, BlockState state) {
        entity.setChanged();
        world.sendBlockUpdated(pos, state, state, 3);
        displayInteraction(world, pos);
    }

    private void displayInteraction(Level world, BlockPos pos){
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            serverWorld.sendParticles(ParticleTypes.SWEEP_ATTACK, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof GenericSmallStorageEntity entity){
            entity.updateViewerCount(world, pos, state);
        }
    }

    /**
     * Vanilla constructors
     * @return
     */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(Barrel::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, FACING, OPEN);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.AXIS, rotation.rotate(state.getValue(BlockStateProperties.AXIS).getPositive()).getAxis());
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.AXIS, mirror.mirror(state.getValue(BlockStateProperties.AXIS).getPositive()).getAxis());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING).getAxis()){
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
                return Shapes.block();
            }
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        shapes = new VoxelShape[3];
        shapes[0] = Shapes.or(
                Shapes.box(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                Shapes.box(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                Shapes.box(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );
        shapes[1] = Shapes.or(
                Shapes.box(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                Shapes.box(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                Shapes.box(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );;
        shapes[2] = Shapes.or(
                Shapes.box(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                Shapes.box(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                Shapes.box(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                Shapes.box(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );;
    }

}
