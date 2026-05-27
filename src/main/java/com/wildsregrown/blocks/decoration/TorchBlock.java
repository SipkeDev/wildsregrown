package com.wildsregrown.blocks.decoration;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
import java.awt.*;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.block.render.TintUtil;

public class TorchBlock extends Block implements ITintedBlock {
    
    protected static final VoxelShape SHAPE = Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.625, 0.5625);
    private final static IntegerProperty FUEL = ModProperties.TORCH_FUEL;
    private final static BooleanProperty LIT = BlockStateProperties.LIT;
    private final static int[] rgb = TintUtil.buildBlend(
            Color.orange.getRGB(),
            Color.black.getRGB(),
            FUEL.getPossibleValues().size()
    );

    public TorchBlock(Properties settings) {
        super(settings
                .lightLevel((state) -> {
                    if (state.getValue(LIT)){
                        return state.getValue(FUEL);
                    }else {
                        return 0;
                    }
                })
                .randomTicks()
        );
        registerDefaultState(defaultBlockState().setValue(FUEL, 15).setValue(LIT, false));
    }

    /**
     * Visuals & Particles
     */
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d = pos.getX() + 0.5;
            double e = pos.getY() + 0.7;
            double f = pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0F, 0.01F, 0.0F);
            world.addParticle(ParticleTypes.ASH, d, e, f, 0.0F, 0.18F, 0.0F);
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.038F, 0.0F);
        }
    }

    /**
     * Random behaviours
     */
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(FUEL) != 0;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        updateState(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(LIT)) {
            if (random.nextInt(18) == 0) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (world.isRaining()){
            if (Dice.d4(random) == 1){
                //todo check for ceiling
                if (state.getValue(LIT)){
                    world.setBlockAndUpdate(pos, state.setValue(LIT, false));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerLevel world, BlockPos pos, RandomSource random){
        if (state.getValue(LIT)) {

            int fuel = state.getValue(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) == 1) {
                    if (fuel == 1){
                        world.setBlockAndUpdate(pos, state.setValue(FUEL, 0).setValue(LIT, false));
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
        if (state.getValue(LIT) && state.getValue(FUEL) == 0){
            world.setBlockAndUpdate(pos, state.setValue(LIT, false));
        }
        if (state.getValue(FUEL) == 0){
            if (Dice.d20(random) == 1){
                world.destroyBlock(pos, false);
            }
        }
    }

    /**
     * Block interactions
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        if (player.swingingArm == null){
            return InteractionResult.FAIL;
        }
        ItemStack heldItem = player.getItemInHand(player.swingingArm);
        Item item = heldItem.getItem();

        if (item == Items.AIR) {

            ItemStack torch = ModItems.torch.getDefaultInstance();
            torch.set(ModComponents.FUEL, state.getValue(FUEL));
            torch.set(ModComponents.LIT, state.getValue(LIT));
            player.setItemInHand(player.swingingArm, torch);
            world.destroyBlock(pos, false);

            return InteractionResult.SUCCESS;
        }

        InteractionResult actionResult = InteractionResult.PASS;

        if (item == Items.FLINT_AND_STEEL){
            actionResult = flintAndSteel(state, world, pos);
        }

        //todo add flamable materials
        if (item == ModItems.scorpion_sword){
            actionResult = updateFuel(state, world, pos);
        }

        return actionResult;
    }

    private InteractionResult updateFuel(BlockState state, Level world, BlockPos pos) {
        world.setBlockAndUpdate(pos, state.setValue(FUEL, FUEL.getPossibleValues().size()));
        return InteractionResult.SUCCESS;
    }

    private InteractionResult flintAndSteel(BlockState state, Level world, BlockPos pos) {
        if (!state.getValue(LIT)) {
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            return InteractionResult.SUCCESS;
        }else {
            return InteractionResult.PASS;
        }
    }

    /**
     * Vanilla constructors
     */
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return direction == Direction.DOWN && !this.canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSupportCenter(world, pos.below(), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FUEL, LIT));
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
            if (state.getValue(LIT)) {
                return rgb[state.getValue(FUEL)];
            }else {
                return rgb[0];
            }
        }
        return -1;
    }

}
