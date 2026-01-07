package com.wildsregrown.blocks.metal.lights;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.FueledLight;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import com.sipke.math.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
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

public class MetalAndIrons extends Block {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;
    private static final IntProperty OXIDATION;
    private static final EnumProperty<FueledLight> FUELED_LIGHT;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public MetalAndIrons(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(FUELED_LIGHT, FueledLight.EMPTY).with(OXIDATION, 0).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0){
            WildsRegrown.LOGGER.info("OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
            WildsRegrown.LOGGER.info("UNLUCKY OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(player.preferredHand);
        Item item = heldItem.getItem();

        if (item == Items.FLINT_AND_STEEL){
            if (state.get(ModProperties.FUELED_LIGHT) == FueledLight.FILLED){
                world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.LIT));
            }
            if (state.get(ModProperties.FUELED_LIGHT) == FueledLight.EMPTY){
                world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.FILLED));
            }
        }
        if (item == Items.GLASS){
            world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
        }
        return ActionResult.PASS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

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
            world.addParticleClient(ParticleTypes.SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
            world.addParticleClient(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
        }
    }

    private boolean isLit(BlockState state){
        return state.get(ModProperties.FUELED_LIGHT) == FueledLight.LIT;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, ModProperties.FUELED_LIGHT, OXIDATION, Properties.WATERLOGGED);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getHorizontalPlayerFacing();
        return getDefaultState().with(FACING, direction.getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
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
        NORTH = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0.1875, 0.125, 0.3125, 0.5625, 0.1875),
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.375, 0.1875, 0.25),
                VoxelShapes.cuboid(-0.01875, 0.04375, 0.13125, 0.24375, 0.10625, 0.24375),
                VoxelShapes.cuboid(0.254419375, 0.039330625, 0.13125, 0.516919375, 0.101830625, 0.24375),
                VoxelShapes.cuboid(0.1875, 0.125, 0.25, 0.3125, 0.1875, 0.875),
                VoxelShapes.cuboid(0.1875, 0, 0.75, 0.3125, 0.125, 0.8125),
                VoxelShapes.cuboid(0.6875, 0.1875, 0.125, 0.8125, 0.5625, 0.1875),
                VoxelShapes.cuboid(0.625, 0.125, 0.125, 0.875, 0.1875, 0.25),
                VoxelShapes.cuboid(0.48125, 0.04375, 0.13125, 0.74375, 0.10625, 0.24375),
                VoxelShapes.cuboid(0.754419375, 0.039330625, 0.13125, 1.016919375, 0.101830625, 0.24375),
                VoxelShapes.cuboid(0.6875, 0.125, 0.25, 0.8125, 0.1875, 0.875),
                VoxelShapes.cuboid(0.6875, 0, 0.75, 0.8125, 0.125, 0.8125)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);

    }
}
