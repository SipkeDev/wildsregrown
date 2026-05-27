package com.wildsregrown.blocks.forging.lights;

import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import com.sipke.math.MathUtil;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.Dice;
import wildsregrown.api.block.render.IRenderType;

public class MetalBrazier extends Block implements IRenderType {

    private static final VoxelShape SHAPE;
    private static final IntegerProperty OXIDATION = ModProperties.OXIDATION;
    private static final IntegerProperty FUEL = ModProperties.FUEL_32;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public MetalBrazier(Properties settings) {
        super(settings.lightLevel(MetalBrazier::calcLuminance).randomTicks());
        this.registerDefaultState(defaultBlockState().setValue(FUEL, 0).setValue(LIT, false).setValue(OXIDATION, 0));
    }

    private static int calcLuminance(BlockState state) {
        return 15;
    }

    /**
     * Visuals & Particles
     */
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {

        if (state.getValue(LIT)) {

            int fuel = state.getValue(FUEL);
            int hash = HashUtil.hash(pos.getX(), pos.getY(), pos.getZ());
            double velX = MathUtil.range(CellType.cellValue.apply(hash, 0), -0.0825f, 0.0825f);
            double velZ = MathUtil.range(CellType.cellValue.apply(hash >> 12, 0), -0.0825f, 0.0825f);

            if (fuel > 25){

                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.75;
                double spawnZ = pos.getZ() + 0.5;

                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, velX, 0.03F, velZ);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, velX, 0.02F, velZ);
                world.addParticle(ParticleTypes.FLAME,  spawnX-0.01f, spawnY, spawnZ, velX, 0.01F, velZ);
                world.addParticle(ParticleTypes.FLAME,  spawnX+0.01f, spawnY, spawnZ, velX, 0.01F, velZ);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ-0.01f, velX, 0.01F, velZ);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ+0.01f, velX, 0.01F, velZ);
                world.addParticle(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, velX, 0.18F, velZ);
                world.addParticle(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, velX, 0.038F, velZ);

            }else if (fuel > 5){
                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.6;
                double spawnZ = pos.getZ() + 0.5;
                world.addParticle(ParticleTypes.FLAME,  spawnX-0.125f, spawnY, spawnZ, 0f, 0.005F, 0f);
                world.addParticle(ParticleTypes.FLAME,  spawnX+0.125f, spawnY, spawnZ, 0f, 0.005F, 0f);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, 0f, 0.01F, 0f);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ-0.125f, 0f, 0.005F, 0f);
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ+0.125f, 0f, 0.005F, 0f);
                world.addParticle(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, 0f, 0.18F, 0f);
                world.addParticle(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, 0f, 0.038F, 0f);
            }else {
                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.3;
                double spawnZ = pos.getZ() + 0.5;
                world.addParticle(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, 0f, 0.01F, 0f);
                world.addParticle(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, 0f, 0.18F, 0f);
                world.addParticle(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, 0f, 0.038F, 0f);
            }

        }
    }

    /**
     * Interactions
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = stack.getItem();

        InteractionResult result = InteractionResult.PASS;

        if (item == Items.FLINT_AND_STEEL){
            result = flintAndSteel(state, world, pos);
        }
        if (item == ModItems.torch){
            result = torch(state, world, pos, player, stack);
        }
        if (item.getName().getString().contains("Beam")){
            result = beam(state, world, pos, player, stack);
        }
        //if (item == WoodGroup.firewood){
            //result = firewood(state, world, pos, player, stack);
        //}

        return result;
    }

    private InteractionResult flintAndSteel(BlockState state, Level world, BlockPos pos) {

        if (Objects.equals(state.getValue(FUEL), FUEL.getPossibleValues().getLast())){
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult torch(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack) {

        boolean torchLit = Boolean.TRUE.equals(stack.get(ModComponents.LIT));

        if (torchLit) {
            if (state.getValue(FUEL) >= 27) {
                world.setBlockAndUpdate(pos, state.setValue(LIT, true));
                return InteractionResult.SUCCESS;
            }
        }else {
            if (state.getValue(LIT)){
                stack.set(ModComponents.LIT, true);
                player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;

    }

    private InteractionResult beam(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack) {

        if (state.getValue(FUEL) < 25){
            world.setBlockAndUpdate(pos, state.setValue(FUEL, MathUtil.min(state.getValue(FUEL)+5, 25)));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

    private InteractionResult firewood(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack) {

        if (state.getValue(FUEL) >= 25 && !Objects.equals(state.getValue(FUEL), FUEL.getPossibleValues().getLast())){
            world.setBlockAndUpdate(pos, state.setValue(FUEL, MathUtil.min(state.getValue(FUEL)+2,FUEL.getPossibleValues().getLast())));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

    /**
     * Random behavior
     * @param state
     * @return
     */

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        setOxidation(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(LIT)) {
            if (Dice.d20(random) == 1) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.isRaining()) {
            if (state.getValue(LIT)) {
                if (Dice.d8(random) == 1) {
                    //todo check for ceiling
                    world.setBlockAndUpdate(pos, state.setValue(LIT, false));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(LIT)) {

            int fuel = state.getValue(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) <= 5) {
                    if (fuel == 1){
                        world.setBlockAndUpdate(pos, state.setValue(FUEL, 0).setValue(LIT, false));
                    }else {
                        world.setBlockAndUpdate(pos, state.setValue(FUEL, fuel-1));
                    }
                }
            }else if (fuel < 25){
                if (Dice.d20(random) == 1) {
                    world.setBlockAndUpdate(pos, state.setValue(FUEL, fuel - 1));
                }
            }else {
                if (Dice.d12(random) == 1) {
                    world.setBlockAndUpdate(pos, state.setValue(FUEL, fuel - 1));
                }
            }

        }
    }

    private void setOxidation(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (Dice.d100(random, 3) <= 9){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) <= 9){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, 3));
        }
    }

    /**
     * Vanilla constructors
     * @param builder
     */

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(OXIDATION, FUEL, LIT);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    static {
        SHAPE = Shapes.or(
                Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.625, 0.3125),
                Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.625, 0.3125),
                Shapes.box(0.1875, 0, 0.6875, 0.3125, 0.625, 0.8125),
                Shapes.box(0.6875, 0, 0.6875, 0.8125, 0.625, 0.8125),
                Shapes.box(0.25, 0.125, 0.25, 0.75, 0.25, 0.75),
                Shapes.box(0.3125, 0.5, 0.1875, 0.6875, 0.5625, 0.25),
                Shapes.box(0.3125, 0.5, 0.75, 0.6875, 0.5625, 0.8125),
                Shapes.box(0.75, 0.5, 0.3125, 0.8125, 0.5625, 0.6875),
                Shapes.box(0.1875, 0.5, 0.3125, 0.25, 0.5625, 0.6875),
                Shapes.box(0.3125, 0.1875, 0.75, 0.6875, 0.3125, 0.8125),
                Shapes.box(0.3125, 0.1875, 0.1875, 0.6875, 0.3125, 0.25),
                Shapes.box(0.75, 0.1875, 0.3125, 0.8125, 0.3125, 0.6875),
                Shapes.box(0.1875, 0.1875, 0.3125, 0.25, 0.3125, 0.6875),
                Shapes.box(0.3125, 0.375, 0.75, 0.6875, 0.4375, 0.8125),
                Shapes.box(0.3125, 0.375, 0.1875, 0.6875, 0.4375, 0.25),
                Shapes.box(0.75, 0.375, 0.3125, 0.8125, 0.4375, 0.6875),
                Shapes.box(0.1875, 0.375, 0.3125, 0.25, 0.4375, 0.6875)
        );
    }
}
