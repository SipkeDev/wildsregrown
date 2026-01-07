package com.wildsregrown.blocks.wood.framing;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.CrateEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Crate extends BlockWithEntity implements BlockEntityProvider, ITintedBlock {

    private static MapCodec<Crate> CODEC;
    private static final VoxelShape[] SHAPE;
    private static final BooleanProperty OPEN = Properties.OPEN;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final Item LID;

    public Crate(Item lid, Settings settings) {
        super(settings);
        this.LID = lid;
        CODEC = createCodec(c0 -> new Crate(lid, c0));
        setDefaultState(getDefaultState().with(OPEN, Boolean.TRUE).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    /**
     * Interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        ActionResult result = ActionResult.PASS;

        if (state.get(OPEN)) {
            if (stack.getItem() == LID) {
                result = placeLid(state, world, pos, stack, player);
            } else {
                if (world.getBlockEntity(pos) instanceof CrateEntity entity) {

                    if (!stack.isEmpty()) {
                        if (entity.store(stack)) {
                            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                            world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                            entity.markDirty();
                            world.updateListeners(pos, state, state, 0);
                            displayInteraction(world, pos);
                            notifyPlayer(player, entity);
                        }
                    }//retrieve
                    else {

                        player.setStackInHand(Hand.MAIN_HAND, entity.retrieve());
                        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);

                        entity.markDirty();
                        world.updateListeners(pos, state, state, 0);
                        displayInteraction(world, pos);
                        notifyPlayer(player, entity);
                    }
                }

                result = ActionResult.SUCCESS;
            }
        } else {
            if (world.getBlockEntity(pos) instanceof CrateEntity entity) {
                notifyPlayer(player, entity);
            }
            result = removeLid(state, world, pos, stack, player);
        }

        return result;
    }

    private void notifyPlayer(PlayerEntity player, CrateEntity entity){
        String text = entity.count == entity.getMaxCount() ? "Full" : String.valueOf(entity.count);
        player.sendMessage(Text.literal(text), true);
    }

    private void displayInteraction(World world, BlockPos pos){
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, (double) pos.getX() + (double) 0.5F, (double) pos.getY() + 1.0, (double) pos.getZ() + (double) 0.5F, 7, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
        }
    }

    private ActionResult placeLid(BlockState state, World world, BlockPos pos, ItemStack stack, PlayerEntity player) {
        stack.decrement(1);
        player.setStackInHand(Hand.MAIN_HAND, stack);
        world.setBlockState(pos, state.with(OPEN, Boolean.FALSE));
        return ActionResult.SUCCESS;
    }

    private ActionResult removeLid(BlockState state, World world, BlockPos pos, ItemStack stack, PlayerEntity player) {

        //Interact lid
        if (stack.getItem() == LID) {
            stack.increment(1);
            player.setStackInHand(Hand.MAIN_HAND, stack);
            world.setBlockState(pos, state.with(OPEN, Boolean.TRUE));
            return ActionResult.SUCCESS;
        } else if (stack.getItem() == Items.AIR) {
            stack = LID.getDefaultStack();
            player.setStackInHand(Hand.MAIN_HAND, stack);
            world.setBlockState(pos, state.with(OPEN, Boolean.TRUE));
            return ActionResult.SUCCESS;
        }else {
            return ActionResult.PASS;
        }
    }

    /**
     * Entity
     */

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrateEntity(pos, state);
    }

    /**
     * VanillaConstructors
     */

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? SHAPE[0] : SHAPE[1];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, PAINT);
    }

    static {
        SHAPE = new VoxelShape[2];
        SHAPE[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0.875, 0, 0, 1, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0, 0.875, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0, 0, 0.875, 0.875, 0.125),
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.125, 0.875)
        );
        SHAPE[1] = VoxelShapes.fullCube();
    }

}
