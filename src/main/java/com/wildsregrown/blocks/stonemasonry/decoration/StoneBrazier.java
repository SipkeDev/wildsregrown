package com.wildsregrown.blocks.stonemasonry.decoration;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.fuel.FueledLight;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.render.IRenderType;

public class StoneBrazier extends Block implements IRenderType {

    private static final VoxelShape SHAPE;
    private static final EnumProperty<FueledLight> FUELED_LIGHT;

    public StoneBrazier(Properties settings) {
        super(settings.lightLevel(ctx -> 15));
        this.registerDefaultState(defaultBlockState().setValue(FUELED_LIGHT, FueledLight.EMPTY).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandItem();
        switch (state.getValue(ModProperties.FUELED_LIGHT)) {
            case FILLED -> {
                if (stack.isEmpty()) {
                    world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
                    world.playSound(player, pos, SoundEvents.WOOD_FALL, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.SUCCESS;
                }
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.LIT));
                    world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.CONSUME;
                }
            }
            case LIT -> {
                if (stack.isEmpty()) {
                    world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.EMPTY));
                    world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.SUCCESS;
                }
            }
            default -> {
                if (stack.is(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WildsRegrown.modid, "branches")))) {
                    world.setBlockAndUpdate(pos, state.setValue(ModProperties.FUELED_LIGHT, FueledLight.FILLED));
                    world.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
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
            double velX = CellType.cellValue.apply(hash, 0)*0.0625;
            double velZ = CellType.cellValue.apply(hash >> 12, 0)*0.0625;
            double velY = 0.0625 + (velX*velZ);
            world.addParticle(ParticleTypes.SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnX, spawnY, spawnZ, velX, velY, velZ);
        }
    }

    private boolean isLit(BlockState state){
        return state.getValue(ModProperties.FUELED_LIGHT) == FueledLight.LIT;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(ModProperties.FUELED_LIGHT, BlockStateProperties.WATERLOGGED);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    static {
        FUELED_LIGHT = ModProperties.FUELED_LIGHT;
        SHAPE = Shapes.or(
                Shapes.box(0, 0, 0, 1, 0.125, 1),
                Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.25, 0.8125),
                Shapes.box(0.125, 0.5, 0.125, 0.875, 0.625, 0.875),
                Shapes.box(0.25, 0.25, 0.25, 0.75, 0.5, 0.75),
                Shapes.box(0.0625, 0.625, 0.0625, 0.1875, 0.875, 0.9375),
                Shapes.box(0.8125, 0.625, 0.0625, 0.9375, 0.875, 0.9375),
                Shapes.box(0.1875, 0.625, 0.0625, 0.8125, 0.875, 0.1875),
                Shapes.box(0.1875, 0.625, 0.8125, 0.8125, 0.875, 0.9375),
                Shapes.box(0, 0, 0, 0.125, 0.125, 0.125)
        );
    }
}
