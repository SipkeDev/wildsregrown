package com.wildsregrown.blocks;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.render.TintUtil;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.awt.*;

public class TorchBlock extends Block implements ITintedBlock {
    
    protected static final VoxelShape SHAPE = VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 0.625, 0.5625);
    private final static IntProperty FUEL = ModProperties.TORCH_FUEL;
    private final static BooleanProperty LIT = Properties.LIT;
    private final static int[] rgb = TintUtil.buildBlend(
            Color.orange.getRGB(),
            Color.black.getRGB(),
            FUEL.getValues().size()
    );

    public TorchBlock(Settings settings) {
        super(settings
                .luminance((state) -> {
                    if (state.get(LIT)){
                        return state.get(FUEL);
                    }else {
                        return 0;
                    }
                })
                .ticksRandomly()
        );
        setDefaultState(getDefaultState().with(FUEL, 15).with(LIT, false));
    }

    /**
     * Visuals & Particles
     */
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = pos.getX() + 0.5;
            double e = pos.getY() + 0.7;
            double f = pos.getZ() + 0.5;
            world.addParticleClient(ParticleTypes.FLAME, d, e, f, 0.0F, 0.01F, 0.0F);
            world.addParticleClient(ParticleTypes.ASH, d, e, f, 0.0F, 0.18F, 0.0F);
            world.addParticleClient(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.038F, 0.0F);
        }
    }

    /**
     * Random behaviours
     */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(FUEL) != 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        playSounds(state, world, pos, random);
        isGettingWet(state, world, pos, random);
        fuelConsumption(state, world, pos, random);
        updateState(state, world, pos, random);
    }

    private void playSounds(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(LIT)) {
            if (random.nextInt(18) == 0) {
                //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, true);
            }
        }
    }

    private void isGettingWet(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (world.isRaining()){
            if (Dice.d4(random) == 1){
                //todo check for ceiling
                if (state.get(LIT)){
                    world.setBlockState(pos, state.with(LIT, false));
                }
            }
        }
    }

    private void fuelConsumption(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (state.get(LIT)) {

            int fuel = state.get(FUEL);
            if (fuel < 5) {
                if (Dice.d100(random) == 1) {
                    if (fuel == 1){
                        world.setBlockState(pos, state.with(FUEL, 0).with(LIT, false));
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
        if (state.get(LIT) && state.get(FUEL) == 0){
            world.setBlockState(pos, state.with(LIT, false));
        }
        if (state.get(FUEL) == 0){
            if (Dice.d20(random) == 1){
                world.breakBlock(pos, false);
            }
        }
    }

    /**
     * Block interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (player.preferredHand == null){
            return ActionResult.FAIL;
        }
        ItemStack heldItem = player.getStackInHand(player.preferredHand);
        Item item = heldItem.getItem();

        if (item == Items.AIR) {

            ItemStack torch = ModItems.torch.getDefaultStack();
            torch.set(ModComponents.FUEL, state.get(FUEL));
            torch.set(ModComponents.LIT, state.get(LIT));
            player.setStackInHand(player.preferredHand, torch);
            world.breakBlock(pos, false);

            return ActionResult.SUCCESS;
        }

        ActionResult actionResult = ActionResult.PASS;

        if (item == Items.FLINT_AND_STEEL){
            actionResult = flintAndSteel(state, world, pos);
        }

        //todo add flamable materials
        if (item == ModItems.scorpion_sword){
            actionResult = updateFuel(state, world, pos);
        }

        return actionResult;
    }

    private ActionResult updateFuel(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(FUEL, FUEL.getValues().size()));
        return ActionResult.SUCCESS;
    }

    private ActionResult flintAndSteel(BlockState state, World world, BlockPos pos) {
        if (!state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, true));
            return ActionResult.SUCCESS;
        }else {
            return ActionResult.PASS;
        }
    }

    /**
     * Vanilla constructors
     */
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(FUEL, LIT));
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
            if (state.get(LIT)) {
                return rgb[state.get(FUEL)];
            }else {
                return rgb[0];
            }
        }
        return -1;
    }

}
