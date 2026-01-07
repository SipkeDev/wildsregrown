package com.wildsregrown.blocks.dungeon;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.ItemLootPedestalEntity;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
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

public class DungeonItemLootPedestal extends BlockWithEntity {

    private static final VoxelShape SHAPE_VAR_1;
    private static final VoxelShape[] SHAPE_VAR_2;
    private static final IntProperty VARS = ModProperties.VARIATIONS_2;
    private static final BooleanProperty ENABLED = Properties.ENABLED;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public DungeonItemLootPedestal(Settings settings){
        super(settings);
        setDefaultState(getDefaultState().with(VARS, 1).with(FACING, Direction.NORTH).with(ENABLED, false));
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ENABLED)){
            if (state.get(VARS) == 1){
                var1Particles(world, pos, random);
            }else {
                var2Particles(world, pos, random);
            }
        }
    }

    private void var1Particles(World world, BlockPos pos, Random random) {
        double spawnX = pos.getX() + 0.125 + (random.nextDouble()*0.625);
        double spawnY = pos.getY() + 0.8 + (random.nextDouble()*0.38);
        double spawnZ = pos.getZ() + 0.125 + (random.nextDouble()*0.625);
        world.addParticleClient(ParticleTypes.REVERSE_PORTAL, spawnX, spawnY, spawnZ, 0, -0.12, 0);
    }

    private void var2Particles(World world, BlockPos pos, Random random) {
        double spawnX = pos.getX() + 0.25 + (random.nextDouble()*0.5);
        double spawnY = pos.getY() + 1.25 + (random.nextDouble()*0.25);
        double spawnZ = pos.getZ() + 0.25 + (random.nextDouble()*0.5);
        world.addParticleClient(ParticleTypes.REVERSE_PORTAL, spawnX, spawnY, spawnZ, 0, -0.05, 0);
    }


    /**
     * Entity
     * @return
     */

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof ItemLootPedestalEntity entity){

            if (entity.isEmpty() && !player.getMainHandStack().isEmpty()){
                world.setBlockState(pos, state.with(ENABLED, true));
                entity.placeItem(player);
                return ActionResult.SUCCESS;
            }else if (player.getMainHandStack().isEmpty()){
                world.setBlockState(pos, state.with(ENABLED, false));
                entity.takeItem(player);
                return ActionResult.SUCCESS;
            }

        }
        return ActionResult.PASS;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(DungeonItemLootPedestal::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemLootPedestalEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModEntities.itemLootPedestal, ItemLootPedestalEntity::tick);
    }

    /**
     * Constructors
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VARS, FACING, ENABLED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    protected boolean emitsRedstonePower(BlockState state) {
        return state.get(ENABLED);
    }

    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(ENABLED) ? 15 : 0;
    }

    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(ENABLED)){
            boolean dir = state.get(FACING) == direction;
            return dir ? 15 : 0;
        }else {
            return 0;
        }
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(VARS) == 1){
            return SHAPE_VAR_1;
        }
        switch (state.get(FACING)){
            case NORTH -> {return SHAPE_VAR_2[0];}
            case EAST -> {return SHAPE_VAR_2[1];}
            case SOUTH -> {return SHAPE_VAR_2[2];}
            case WEST -> {return SHAPE_VAR_2[3];}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        SHAPE_VAR_1 = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1),
                VoxelShapes.cuboid(0.125, 0.25, 0.125, 0.875, 0.5, 0.875),
                VoxelShapes.cuboid(0.0625, 0.25, 0.0625, 0.3125, 0.75, 0.3125),
                VoxelShapes.cuboid(0.6875, 0.25, 0.0625, 0.9375, 0.75, 0.3125),
                VoxelShapes.cuboid(0.0625, 0.25, 0.6875, 0.3125, 0.75, 0.9375),
                VoxelShapes.cuboid(0.6875, 0.25, 0.6875, 0.9375, 0.75, 0.9375)
        );

        SHAPE_VAR_2 = new VoxelShape[4];
        SHAPE_VAR_2[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0, 0, 0.8125, 0.125, 0.625),
                VoxelShapes.cuboid(0.25, 0.125, 0, 0.75, 0.625, 0.5),
                VoxelShapes.cuboid(0.25, 0.5, 0.5, 0.75, 0.625, 0.875),
                VoxelShapes.cuboid(0.125, 0.625, 0.25, 0.875, 0.75, 0.5625),
                VoxelShapes.cuboid(0, 0.625, -0.0625, 1, 0.875, 0.25),
                VoxelShapes.cuboid(0, 0.875, -0.125, 1, 1, 0)
        );
        SHAPE_VAR_2[1] = VoxelTransform.rotate90(SHAPE_VAR_2[0]);
        SHAPE_VAR_2[2] = VoxelTransform.rotate180(SHAPE_VAR_2[0]);
        SHAPE_VAR_2[3] = VoxelTransform.rotate270(SHAPE_VAR_2[0]);
    }

}
