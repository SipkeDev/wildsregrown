package com.wildsregrown.blocks.stone;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.FueledLight;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class StoneBrazier extends Block {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<FueledLight> FUELED_LIGHT;

    public StoneBrazier(Settings settings) {
        super(settings.luminance(ctx -> 15));
        this.setDefaultState(getDefaultState().with(FUELED_LIGHT, FueledLight.EMPTY).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();
        switch (state.get(ModProperties.FUELED_LIGHT)) {
            case FILLED -> {
                if (stack.isEmpty()) {
                    world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
                    world.playSound(player, pos, SoundEvents.BLOCK_WOOD_FALL, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
                if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.LIT));
                    world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.CONSUME;
                }
            }
            case LIT -> {
                if (stack.isEmpty()) {
                    world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
                    world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
            }
            default -> {
                if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(WildsRegrown.modid, "branches")))) {
                    world.setBlockState(pos, state.with(ModProperties.FUELED_LIGHT, FueledLight.FILLED));
                    world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
            double velX = CellType.cellValue.apply(hash, 0)*0.0625;
            double velZ = CellType.cellValue.apply(hash >> 12, 0)*0.0625;
            double velY = 0.0625 + (velX*velZ);
            world.addParticleClient(ParticleTypes.SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
            world.addParticleClient(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
        }
    }

    private boolean isLit(BlockState state){
        return state.get(ModProperties.FUELED_LIGHT) == FueledLight.LIT;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(ModProperties.FUELED_LIGHT, Properties.WATERLOGGED);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return SHAPE;
    }

    static {
        FUELED_LIGHT = ModProperties.FUELED_LIGHT;
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1),
                VoxelShapes.cuboid(0.1875, 0.125, 0.1875, 0.8125, 0.25, 0.8125),
                VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.625, 0.875),
                VoxelShapes.cuboid(0.25, 0.25, 0.25, 0.75, 0.5, 0.75),
                VoxelShapes.cuboid(0.0625, 0.625, 0.0625, 0.1875, 0.875, 0.9375),
                VoxelShapes.cuboid(0.8125, 0.625, 0.0625, 0.9375, 0.875, 0.9375),
                VoxelShapes.cuboid(0.1875, 0.625, 0.0625, 0.8125, 0.875, 0.1875),
                VoxelShapes.cuboid(0.1875, 0.625, 0.8125, 0.8125, 0.875, 0.9375),
                VoxelShapes.cuboid(0, 0, 0, 0.125, 0.125, 0.125)
        );
    }
}
