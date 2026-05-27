package com.wildsregrown.blocks.forging;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.metal.TorchHolderState;
import com.sipke.math.MathUtil;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
import java.awt.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
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
import wildsregrown.api.block.Dice;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.block.render.TintUtil;

public class MetalTorchHolder extends Block implements ITintedBlock {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;
    private static final IntegerProperty OXIDATION;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final IntegerProperty FUEL = ModProperties.TORCH_FUEL;
    private static final EnumProperty<TorchHolderState> HOLDER_STATE = ModProperties.TORCH_HOLDER_STATE;
    private final static int[] rgb = TintUtil.buildBlend(
            Color.orange.getRGB(),
            Color.black.getRGB(),
            FUEL.getPossibleValues().size()
    );

    public MetalTorchHolder(Properties settings) {
        super(settings.lightLevel(MetalTorchHolder::calcLuminance).randomTicks());
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ModProperties.TORCH_FUEL, 0).setValue(OXIDATION, 0).setValue(HOLDER_STATE, TorchHolderState.EMPTY));
    }

    private static int calcLuminance(BlockState state) {
        if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT){
            return state.getValue(FUEL);
        }else {
            return 0;
        }
    }

    /**
     * Visuals & Particles
     */
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT) {
            double d = pos.getX() + 0.5;
            double e = pos.getY() + 0.7;
            double f = pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0F, 0.01F, 0.0F);
            world.addParticle(ParticleTypes.ASH, d, e, f, 0.0F, 0.18F, 0.0F);
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.038F, 0.0F);
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
        if (item == Items.AIR){
            result = getTorch(state, world, pos, player, stack);
        }

        return result;
    }

    private InteractionResult flintAndSteel(BlockState state, Level world, BlockPos pos) {

        if (state.getValue(HOLDER_STATE) == TorchHolderState.UNLIT){
            world.setBlockAndUpdate(pos, state.setValue(HOLDER_STATE, TorchHolderState.LIT));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult torch(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack) {

        if (state.getValue(HOLDER_STATE) == TorchHolderState.EMPTY){
            world.setBlockAndUpdate(pos, state.setValue(HOLDER_STATE, Boolean.TRUE.equals(stack.get(ModComponents.LIT)) ? TorchHolderState.UNLIT : TorchHolderState.LIT));
            stack.shrink(1);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

    private InteractionResult getTorch(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack) {

        if (state.getValue(HOLDER_STATE) == TorchHolderState.UNLIT || state.getValue(HOLDER_STATE) == TorchHolderState.LIT){
            world.setBlockAndUpdate(pos, state.setValue(HOLDER_STATE, TorchHolderState.EMPTY));
            ItemStack stack1 = ModItems.torch.getDefaultInstance();
            stack1.set(ModComponents.FUEL, state.getValue(FUEL));
            stack1.set(ModComponents.LIT, state.getValue(HOLDER_STATE) == TorchHolderState.LIT);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack1);
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
        return state.getValue(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        setOxidation(state, world, pos, random);
        updateState(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT) {
            if (random.nextInt(18) == 0) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.isRaining()) {
            if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT) {
                if (Dice.d4(random) == 1) {
                    //todo check for ceiling
                    world.setBlockAndUpdate(pos, state.setValue(HOLDER_STATE, TorchHolderState.UNLIT));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT) {

            int fuel = state.getValue(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) == 1) {
                    if (fuel == 1){
                        world.setBlockAndUpdate(pos, state.setValue(FUEL, 0).setValue(HOLDER_STATE, TorchHolderState.UNLIT));
                    }else {
                        world.setBlockAndUpdate(pos, state.setValue(FUEL, fuel-1));
                    }
                }
            }else {
                if (Dice.d20(random) == 1) {
                    world.setBlockAndUpdate(pos, state.setValue(FUEL, fuel - 1));
                }
            }

        }
    }

    private void updateState(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT && state.getValue(FUEL) == 0){
            world.setBlockAndUpdate(pos, state.setValue(HOLDER_STATE, TorchHolderState.UNLIT));
        }
    }

    private void setOxidation(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (Dice.d100(random) == 1){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) < 9){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, 3));
        }
    }

    /**
     * Vanilla Constructors
     * @param builder
     */

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, OXIDATION, HOLDER_STATE, FUEL);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return defaultBlockState().setValue(FACING, direction);
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

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        NORTH = Shapes.or(
                Shapes.box(0.3125, 0, 0.875, 0.6875, 0.75, 1),
                Shapes.box(0.3125, 0, 0.75, 0.6875, 0.125, 0.875),
                Shapes.box(0.3125, 0.125, 0.75, 0.4375, 0.3125, 0.875),
                Shapes.box(0.5625, 0.125, 0.75, 0.6875, 0.3125, 0.875)
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
            if (state.getValue(HOLDER_STATE) == TorchHolderState.LIT) {
                return rgb[state.getValue(FUEL)];
            }else {
                return rgb[0];
            }
        }
        return -1;
    }

}
