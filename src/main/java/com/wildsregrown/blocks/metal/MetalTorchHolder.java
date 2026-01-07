package com.wildsregrown.blocks.metal;

import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.TorchHolderState;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.render.TintUtil;
import com.sipke.math.MathUtil;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
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
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.awt.*;

public class MetalTorchHolder extends Block implements ITintedBlock {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;
    private static final IntProperty OXIDATION;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final IntProperty FUEL = ModProperties.TORCH_FUEL;
    private static final EnumProperty<TorchHolderState> HOLDER_STATE = ModProperties.TORCH_HOLDER_STATE;
    private final static int[] rgb = TintUtil.buildBlend(
            Color.orange.getRGB(),
            Color.black.getRGB(),
            FUEL.getValues().size()
    );

    public MetalTorchHolder(Settings settings) {
        super(settings.luminance(MetalTorchHolder::calcLuminance).ticksRandomly());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(ModProperties.TORCH_FUEL, 0).with(OXIDATION, 0).with(HOLDER_STATE, TorchHolderState.EMPTY));
    }

    private static int calcLuminance(BlockState state) {
        if (state.get(HOLDER_STATE) == TorchHolderState.LIT){
            return state.get(FUEL);
        }else {
            return 0;
        }
    }

    /**
     * Visuals & Particles
     */
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(HOLDER_STATE) == TorchHolderState.LIT) {
            double d = pos.getX() + 0.5;
            double e = pos.getY() + 0.7;
            double f = pos.getZ() + 0.5;
            world.addParticleClient(ParticleTypes.FLAME, d, e, f, 0.0F, 0.01F, 0.0F);
            world.addParticleClient(ParticleTypes.ASH, d, e, f, 0.0F, 0.18F, 0.0F);
            world.addParticleClient(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.038F, 0.0F);
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
        if (item == Items.AIR){
            result = getTorch(state, world, pos, player, stack);
        }

        return result;
    }

    private ActionResult flintAndSteel(BlockState state, World world, BlockPos pos) {

        if (state.get(HOLDER_STATE) == TorchHolderState.UNLIT){
            world.setBlockState(pos, state.with(HOLDER_STATE, TorchHolderState.LIT));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult torch(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        if (state.get(HOLDER_STATE) == TorchHolderState.EMPTY){
            world.setBlockState(pos, state.with(HOLDER_STATE, Boolean.TRUE.equals(stack.get(ModComponents.LIT)) ? TorchHolderState.UNLIT : TorchHolderState.LIT));
            stack.decrement(1);
            player.setStackInHand(Hand.MAIN_HAND, stack);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

    private ActionResult getTorch(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        if (state.get(HOLDER_STATE) == TorchHolderState.UNLIT || state.get(HOLDER_STATE) == TorchHolderState.LIT){
            world.setBlockState(pos, state.with(HOLDER_STATE, TorchHolderState.EMPTY));
            ItemStack stack1 = ModItems.torch.getDefaultStack();
            stack1.set(ModComponents.FUEL, state.get(FUEL));
            stack1.set(ModComponents.LIT, state.get(HOLDER_STATE) == TorchHolderState.LIT);
            player.setStackInHand(Hand.MAIN_HAND, stack1);
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
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        setOxidation(state, world, pos, random);
        updateState(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(HOLDER_STATE) == TorchHolderState.LIT) {
            if (random.nextInt(18) == 0) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isRaining()) {
            if (state.get(HOLDER_STATE) == TorchHolderState.LIT) {
                if (Dice.d4(random) == 1) {
                    //todo check for ceiling
                    world.setBlockState(pos, state.with(HOLDER_STATE, TorchHolderState.UNLIT));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(HOLDER_STATE) == TorchHolderState.LIT) {

            int fuel = state.get(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) == 1) {
                    if (fuel == 1){
                        world.setBlockState(pos, state.with(FUEL, 0).with(HOLDER_STATE, TorchHolderState.UNLIT));
                    }else {
                        world.setBlockState(pos, state.with(FUEL, fuel-1));
                    }
                }
            }else {
                if (Dice.d20(random) == 1) {
                    world.setBlockState(pos, state.with(FUEL, fuel - 1));
                }
            }

        }
    }

    private void updateState(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HOLDER_STATE) == TorchHolderState.LIT && state.get(FUEL) == 0){
            world.setBlockState(pos, state.with(HOLDER_STATE, TorchHolderState.UNLIT));
        }
    }

    private void setOxidation(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Dice.d100(random) == 1){
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) < 9){
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }

    /**
     * Vanilla Constructors
     * @param builder
     */

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, OXIDATION, HOLDER_STATE, FUEL);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(FACING, direction);
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

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        NORTH = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0, 0.875, 0.6875, 0.75, 1),
                VoxelShapes.cuboid(0.3125, 0, 0.75, 0.6875, 0.125, 0.875),
                VoxelShapes.cuboid(0.3125, 0.125, 0.75, 0.4375, 0.3125, 0.875),
                VoxelShapes.cuboid(0.5625, 0.125, 0.75, 0.6875, 0.3125, 0.875)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }

    /**
     * Custom tint
     * @param state current blockstate
     * @param tintIndex which face(s) with this index are rendered
     * @return color rgb int
     * Also totally fun if some pushes the fuel beyond 15
     */
    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 1){
            if (state.get(HOLDER_STATE) == TorchHolderState.LIT) {
                return rgb[state.get(FUEL)];
            }else {
                return rgb[0];
            }
        }
        return -1;
    }

}
