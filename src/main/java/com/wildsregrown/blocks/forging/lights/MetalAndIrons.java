package com.wildsregrown.blocks.forging.lights;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.fuel.FueledLight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import com.sipke.math.MathUtil;
import wildsregrown.api.block.VoxelTransform;

public class MetalAndIrons extends Block {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;
    private static final IntegerProperty OXIDATION;
    private static final EnumProperty<FueledLight> FUELED_LIGHT;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MetalAndIrons(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(FUELED_LIGHT, FueledLight.EMPTY).setValue(OXIDATION, 0).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0){
            WildsRegrown.LOGGER.info("OXIDATION HAPPENED");
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
            WildsRegrown.LOGGER.info("UNLUCKY OXIDATION HAPPENED");
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, 3));
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(player.swingingArm);
        Item item = heldItem.getItem();

        if (item == Items.FLINT_AND_STEEL){
            if (state.getValue(ModProperties.FUELED_LIGHT) == FueledLight.FILLED){
                world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.LIT));
            }
            if (state.getValue(ModProperties.FUELED_LIGHT) == FueledLight.EMPTY){
                world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.FILLED));
            }
        }
        if (item == Items.GLASS){
            world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {

        double spawnX = pos.getX() + 0.5;
        double spawnY = pos.getY() + 0.7;
        double spawnZ = pos.getZ() + 0.5;

        if (random.nextInt(18) == 0) {
            //world.playSound(spawnX, spawnY, spawnZ, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
        if (random.nextInt(10) == 0) {
            //world.playSound(spawnX, spawnY, spawnZ, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, (float) (0.6F + random.nextFloat() * 0.3), random.nextFloat() * 0.6F + 0.5F, false);
        }
        if (isLit(state)) {
            int hash = HashUtil.hash((int) spawnY, (int) spawnX, (int) spawnZ);
            double velX = (CellType.cellValue.apply(hash, 0)-0.5)*0.0625;
            double velZ = (CellType.cellValue.apply(hash >> 12, 0)-0.5)*0.0625;
            double velY = (velX+velZ);
            world.addParticle(ParticleTypes.SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
        }
    }

    private boolean isLit(BlockState state){
        return state.getValue(ModProperties.FUELED_LIGHT) == FueledLight.LIT;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, ModProperties.FUELED_LIGHT, OXIDATION, BlockStateProperties.WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        return defaultBlockState().setValue(FACING, direction.getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(FACING)) {
            case NORTH -> {return NORTH;}
            case SOUTH  -> {return SOUTH;}
            case EAST  -> {return EAST;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        FUELED_LIGHT = ModProperties.FUELED_LIGHT;
        NORTH = Shapes.or(
                Shapes.box(0.1875, 0.1875, 0.125, 0.3125, 0.5625, 0.1875),
                Shapes.box(0.125, 0.125, 0.125, 0.375, 0.1875, 0.25),
                Shapes.box(-0.01875, 0.04375, 0.13125, 0.24375, 0.10625, 0.24375),
                Shapes.box(0.254419375, 0.039330625, 0.13125, 0.516919375, 0.101830625, 0.24375),
                Shapes.box(0.1875, 0.125, 0.25, 0.3125, 0.1875, 0.875),
                Shapes.box(0.1875, 0, 0.75, 0.3125, 0.125, 0.8125),
                Shapes.box(0.6875, 0.1875, 0.125, 0.8125, 0.5625, 0.1875),
                Shapes.box(0.625, 0.125, 0.125, 0.875, 0.1875, 0.25),
                Shapes.box(0.48125, 0.04375, 0.13125, 0.74375, 0.10625, 0.24375),
                Shapes.box(0.754419375, 0.039330625, 0.13125, 1.016919375, 0.101830625, 0.24375),
                Shapes.box(0.6875, 0.125, 0.25, 0.8125, 0.1875, 0.875),
                Shapes.box(0.6875, 0, 0.75, 0.8125, 0.125, 0.8125)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);

    }
}
