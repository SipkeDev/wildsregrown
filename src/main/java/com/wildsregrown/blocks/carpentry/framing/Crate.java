package com.wildsregrown.blocks.carpentry.framing;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.CrateEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.render.ITintedBlock;

public class Crate extends BaseEntityBlock implements EntityBlock, ITintedBlock {

    private static MapCodec<Crate> CODEC;
    private static final VoxelShape[] SHAPE;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final Item LID;

    public Crate(Item lid, Properties settings) {
        super(settings);
        this.LID = lid;
        CODEC = simpleCodec(c0 -> new Crate(lid, c0));
        registerDefaultState(defaultBlockState().setValue(OPEN, Boolean.TRUE).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    /**
     * Interactions
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        InteractionResult result = InteractionResult.PASS;

        if (state.getValue(OPEN)) {
            if (stack.getItem() == LID) {
                result = placeLid(state, world, pos, stack, player);
            } else {
                if (world.getBlockEntity(pos) instanceof CrateEntity entity) {

                    if (!stack.isEmpty()) {
                        if (entity.store(stack)) {
                            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                            world.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                            entity.setChanged();
                            world.sendBlockUpdated(pos, state, state, 0);
                            displayInteraction(world, pos);
                            notifyPlayer(player, entity);
                        }
                    }//retrieve
                    else {

                        player.setItemInHand(InteractionHand.MAIN_HAND, entity.retrieve());
                        world.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);

                        entity.setChanged();
                        world.sendBlockUpdated(pos, state, state, 0);
                        displayInteraction(world, pos);
                        notifyPlayer(player, entity);
                    }
                }

                result = InteractionResult.SUCCESS;
            }
        } else {
            if (world.getBlockEntity(pos) instanceof CrateEntity entity) {
                notifyPlayer(player, entity);
            }
            result = removeLid(state, world, pos, stack, player);
        }

        return result;
    }

    private void notifyPlayer(Player player, CrateEntity entity){
        String text = entity.count == entity.getMaxCount() ? "Full" : String.valueOf(entity.count);
        player.displayClientMessage(Component.literal(text), true);
    }

    private void displayInteraction(Level world, BlockPos pos){
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            serverWorld.sendParticles(ParticleTypes.DUST_PLUME, (double) pos.getX() + (double) 0.5F, (double) pos.getY() + 1.0, (double) pos.getZ() + (double) 0.5F, 7, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
        }
    }

    private InteractionResult placeLid(BlockState state, Level world, BlockPos pos, ItemStack stack, Player player) {
        stack.shrink(1);
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        world.setBlockAndUpdate(pos, state.setValue(OPEN, Boolean.FALSE));
        return InteractionResult.SUCCESS;
    }

    private InteractionResult removeLid(BlockState state, Level world, BlockPos pos, ItemStack stack, Player player) {

        //Interact lid
        if (stack.getItem() == LID) {
            stack.grow(1);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            world.setBlockAndUpdate(pos, state.setValue(OPEN, Boolean.TRUE));
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.AIR) {
            stack = LID.getDefaultInstance();
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            world.setBlockAndUpdate(pos, state.setValue(OPEN, Boolean.TRUE));
            return InteractionResult.SUCCESS;
        }else {
            return InteractionResult.PASS;
        }
    }

    /**
     * Entity
     */

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrateEntity(pos, state);
    }

    /**
     * VanillaConstructors
     */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? SHAPE[0] : SHAPE[1];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN, PAINT);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        SHAPE = new VoxelShape[2];
        SHAPE[0] = Shapes.or(
                Shapes.box(0, 0, 0, 0.125, 0.875, 1),
                Shapes.box(0.875, 0, 0, 1, 0.875, 1),
                Shapes.box(0.125, 0, 0.875, 0.875, 0.875, 1),
                Shapes.box(0.125, 0, 0, 0.875, 0.875, 0.125),
                Shapes.box(0.125, 0, 0.125, 0.875, 0.125, 0.875)
        );
        SHAPE[1] = Shapes.block();
    }

}
