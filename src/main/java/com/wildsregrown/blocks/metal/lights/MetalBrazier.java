package com.wildsregrown.blocks.metal.lights;

import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.CellType;
import com.sipke.math.HashUtil;
import com.sipke.math.MathUtil;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Objects;

public class MetalBrazier extends Block {

    private static final VoxelShape SHAPE;
    private static final IntProperty OXIDATION = ModProperties.OXIDATION;
    private static final IntProperty FUEL = ModProperties.FUEL_32;
    private static final BooleanProperty LIT = Properties.LIT;

    public MetalBrazier(Settings settings) {
        super(settings.luminance(MetalBrazier::calcLuminance).ticksRandomly());
        this.setDefaultState(getDefaultState().with(FUEL, 0).with(LIT, false).with(OXIDATION, 0));
    }

    private static int calcLuminance(BlockState state) {
        return 15;
    }

    /**
     * Visuals & Particles
     */
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if (state.get(LIT)) {

            int fuel = state.get(FUEL);
            int hash = HashUtil.hash(pos.getX(), pos.getY(), pos.getZ());
            double velX = MathUtil.range(CellType.cellValue.apply(hash, 0), -0.0825f, 0.0825f);
            double velZ = MathUtil.range(CellType.cellValue.apply(hash >> 12, 0), -0.0825f, 0.0825f);

            if (fuel > 25){

                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.75;
                double spawnZ = pos.getZ() + 0.5;

                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, velX, 0.03F, velZ);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, velX, 0.02F, velZ);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX-0.01f, spawnY, spawnZ, velX, 0.01F, velZ);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX+0.01f, spawnY, spawnZ, velX, 0.01F, velZ);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ-0.01f, velX, 0.01F, velZ);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ+0.01f, velX, 0.01F, velZ);
                world.addParticleClient(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, velX, 0.18F, velZ);
                world.addParticleClient(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, velX, 0.038F, velZ);

            }else if (fuel > 5){
                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.6;
                double spawnZ = pos.getZ() + 0.5;
                world.addParticleClient(ParticleTypes.FLAME,  spawnX-0.125f, spawnY, spawnZ, 0f, 0.005F, 0f);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX+0.125f, spawnY, spawnZ, 0f, 0.005F, 0f);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, 0f, 0.01F, 0f);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ-0.125f, 0f, 0.005F, 0f);
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ+0.125f, 0f, 0.005F, 0f);
                world.addParticleClient(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, 0f, 0.18F, 0f);
                world.addParticleClient(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, 0f, 0.038F, 0f);
            }else {
                double spawnX = pos.getX() + 0.5;
                double spawnY = pos.getY() + 0.3;
                double spawnZ = pos.getZ() + 0.5;
                world.addParticleClient(ParticleTypes.FLAME,  spawnX, spawnY, spawnZ, 0f, 0.01F, 0f);
                world.addParticleClient(ParticleTypes.ASH,    spawnX, spawnY, spawnZ, 0f, 0.18F, 0f);
                world.addParticleClient(ParticleTypes.SMOKE,  spawnX, spawnY, spawnZ, 0f, 0.038F, 0f);
            }

        }
    }

    /**
     * Interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        Item item = stack.getItem();

        ActionResult result = ActionResult.PASS;

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

    private ActionResult flintAndSteel(BlockState state, World world, BlockPos pos) {

        if (Objects.equals(state.get(FUEL), FUEL.getValues().getLast())){
            world.setBlockState(pos, state.with(LIT, true));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult torch(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        boolean torchLit = Boolean.TRUE.equals(stack.get(ModComponents.LIT));

        if (torchLit) {
            if (state.get(FUEL) >= 27) {
                world.setBlockState(pos, state.with(LIT, true));
                return ActionResult.SUCCESS;
            }
        }else {
            if (state.get(LIT)){
                stack.set(ModComponents.LIT, true);
                player.setStackInHand(Hand.MAIN_HAND, stack);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;

    }

    private ActionResult beam(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        if (state.get(FUEL) < 25){
            world.setBlockState(pos, state.with(FUEL, MathUtil.min(state.get(FUEL)+5, 25)));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

    private ActionResult firewood(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        if (state.get(FUEL) >= 25 && !Objects.equals(state.get(FUEL), FUEL.getValues().getLast())){
            world.setBlockState(pos, state.with(FUEL, MathUtil.min(state.get(FUEL)+2,FUEL.getValues().getLast())));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

    /**
     * Random behavior
     * @param state
     * @return
     */

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        setOxidation(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(LIT)) {
            if (Dice.d20(random) == 1) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isRaining()) {
            if (state.get(LIT)) {
                if (Dice.d8(random) == 1) {
                    //todo check for ceiling
                    world.setBlockState(pos, state.with(LIT, false));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(LIT)) {

            int fuel = state.get(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) <= 5) {
                    if (fuel == 1){
                        world.setBlockState(pos, state.with(FUEL, 0).with(LIT, false));
                    }else {
                        world.setBlockState(pos, state.with(FUEL, fuel-1));
                    }
                }
            }else if (fuel < 25){
                if (Dice.d20(random) == 1) {
                    world.setBlockState(pos, state.with(FUEL, fuel - 1));
                }
            }else {
                if (Dice.d12(random) == 1) {
                    world.setBlockState(pos, state.with(FUEL, fuel - 1));
                }
            }

        }
    }

    private void setOxidation(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Dice.d100(random, 3) <= 9){
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) <= 9){
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }

    /**
     * Vanilla constructors
     * @param builder
     */

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(OXIDATION, FUEL, LIT);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return SHAPE;
    }

    static {
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.3125, 0.625, 0.3125),
                VoxelShapes.cuboid(0.6875, 0, 0.1875, 0.8125, 0.625, 0.3125),
                VoxelShapes.cuboid(0.1875, 0, 0.6875, 0.3125, 0.625, 0.8125),
                VoxelShapes.cuboid(0.6875, 0, 0.6875, 0.8125, 0.625, 0.8125),
                VoxelShapes.cuboid(0.25, 0.125, 0.25, 0.75, 0.25, 0.75),
                VoxelShapes.cuboid(0.3125, 0.5, 0.1875, 0.6875, 0.5625, 0.25),
                VoxelShapes.cuboid(0.3125, 0.5, 0.75, 0.6875, 0.5625, 0.8125),
                VoxelShapes.cuboid(0.75, 0.5, 0.3125, 0.8125, 0.5625, 0.6875),
                VoxelShapes.cuboid(0.1875, 0.5, 0.3125, 0.25, 0.5625, 0.6875),
                VoxelShapes.cuboid(0.3125, 0.1875, 0.75, 0.6875, 0.3125, 0.8125),
                VoxelShapes.cuboid(0.3125, 0.1875, 0.1875, 0.6875, 0.3125, 0.25),
                VoxelShapes.cuboid(0.75, 0.1875, 0.3125, 0.8125, 0.3125, 0.6875),
                VoxelShapes.cuboid(0.1875, 0.1875, 0.3125, 0.25, 0.3125, 0.6875),
                VoxelShapes.cuboid(0.3125, 0.375, 0.75, 0.6875, 0.4375, 0.8125),
                VoxelShapes.cuboid(0.3125, 0.375, 0.1875, 0.6875, 0.4375, 0.25),
                VoxelShapes.cuboid(0.75, 0.375, 0.3125, 0.8125, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.1875, 0.375, 0.3125, 0.25, 0.4375, 0.6875)
        );
    }
}
