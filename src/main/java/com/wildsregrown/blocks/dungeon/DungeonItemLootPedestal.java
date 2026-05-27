package com.wildsregrown.blocks.dungeon;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.ItemLootPedestalEntity;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;

public class DungeonItemLootPedestal extends BaseEntityBlock {

    private static final VoxelShape SHAPE_VAR_1;
    private static final VoxelShape[] SHAPE_VAR_2;
    private static final IntegerProperty VARS = ModProperties.VARIATIONS_2;
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DungeonItemLootPedestal(Properties settings){
        super(settings);
        registerDefaultState(defaultBlockState().setValue(VARS, 1).setValue(FACING, Direction.NORTH).setValue(ENABLED, false));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(ENABLED)){
            if (state.getValue(VARS) == 1){
                var1Particles(world, pos, random);
            }else {
                var2Particles(world, pos, random);
            }
        }
    }

    private void var1Particles(Level world, BlockPos pos, RandomSource random) {
        double spawnX = pos.getX() + 0.125 + (random.nextDouble()*0.625);
        double spawnY = pos.getY() + 0.8 + (random.nextDouble()*0.38);
        double spawnZ = pos.getZ() + 0.125 + (random.nextDouble()*0.625);
        world.addParticle(ParticleTypes.REVERSE_PORTAL, spawnX, spawnY, spawnZ, 0, -0.12, 0);
    }

    private void var2Particles(Level world, BlockPos pos, RandomSource random) {
        double spawnX = pos.getX() + 0.25 + (random.nextDouble()*0.5);
        double spawnY = pos.getY() + 1.25 + (random.nextDouble()*0.25);
        double spawnZ = pos.getZ() + 0.25 + (random.nextDouble()*0.5);
        world.addParticle(ParticleTypes.REVERSE_PORTAL, spawnX, spawnY, spawnZ, 0, -0.05, 0);
    }


    /**
     * Entity
     * @return
     */

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof ItemLootPedestalEntity entity){

            if (entity.isEmpty() && !player.getMainHandItem().isEmpty()){
                world.setBlockAndUpdate(pos, state.setValue(ENABLED, true));
                entity.placeItem(player);
                return InteractionResult.SUCCESS;
            }else if (player.getMainHandItem().isEmpty()){
                world.setBlockAndUpdate(pos, state.setValue(ENABLED, false));
                entity.takeItem(player);
                return InteractionResult.SUCCESS;
            }

        }
        return InteractionResult.PASS;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DungeonItemLootPedestal::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemLootPedestalEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModEntities.itemLootPedestal, ItemLootPedestalEntity::tick);
    }

    /**
     * Constructors
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARS, FACING, ENABLED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    protected boolean isSignalSource(BlockState state) {
        return state.getValue(ENABLED);
    }

    protected int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(ENABLED) ? 15 : 0;
    }

    protected int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (state.getValue(ENABLED)){
            boolean dir = state.getValue(FACING) == direction;
            return dir ? 15 : 0;
        }else {
            return 0;
        }
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(VARS) == 1){
            return SHAPE_VAR_1;
        }
        switch (state.getValue(FACING)){
            case NORTH -> {return SHAPE_VAR_2[0];}
            case EAST -> {return SHAPE_VAR_2[1];}
            case SOUTH -> {return SHAPE_VAR_2[2];}
            case WEST -> {return SHAPE_VAR_2[3];}
            default -> {return Shapes.block();}
        }
    }

    static {
        SHAPE_VAR_1 = Shapes.or(
                Shapes.box(0, 0, 0, 1, 0.25, 1),
                Shapes.box(0.125, 0.25, 0.125, 0.875, 0.5, 0.875),
                Shapes.box(0.0625, 0.25, 0.0625, 0.3125, 0.75, 0.3125),
                Shapes.box(0.6875, 0.25, 0.0625, 0.9375, 0.75, 0.3125),
                Shapes.box(0.0625, 0.25, 0.6875, 0.3125, 0.75, 0.9375),
                Shapes.box(0.6875, 0.25, 0.6875, 0.9375, 0.75, 0.9375)
        );

        SHAPE_VAR_2 = new VoxelShape[4];
        SHAPE_VAR_2[0] = Shapes.or(
                Shapes.box(0.1875, 0, 0, 0.8125, 0.125, 0.625),
                Shapes.box(0.25, 0.125, 0, 0.75, 0.625, 0.5),
                Shapes.box(0.25, 0.5, 0.5, 0.75, 0.625, 0.875),
                Shapes.box(0.125, 0.625, 0.25, 0.875, 0.75, 0.5625),
                Shapes.box(0, 0.625, -0.0625, 1, 0.875, 0.25),
                Shapes.box(0, 0.875, -0.125, 1, 1, 0)
        );
        SHAPE_VAR_2[1] = VoxelTransform.rotate90(SHAPE_VAR_2[0]);
        SHAPE_VAR_2[2] = VoxelTransform.rotate180(SHAPE_VAR_2[0]);
        SHAPE_VAR_2[3] = VoxelTransform.rotate270(SHAPE_VAR_2[0]);
    }

}
