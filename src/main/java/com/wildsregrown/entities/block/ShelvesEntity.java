package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ShelvesEntity extends BlockEntity {

    private ItemStack stack0;
    private ItemStack stack1;

    public ShelvesEntity(BlockPos pos, BlockState state) {
        super(ModEntities.shelvesEntity, pos, state);
        this.stack0 = ItemStack.EMPTY;
        this.stack1 = ItemStack.EMPTY;
    }

    public ItemStack getStack(int flag) {
        return flag == 0 ? stack0 : stack1;
    }

    public void swapStack(int flag, Player player){
        if (flag == 0){
            ItemStack stack = player.getMainHandItem();
            player.setItemInHand(InteractionHand.MAIN_HAND, stack0);
            stack0 = stack;
        }
        if (flag == 1){
            ItemStack stack = player.getMainHandItem();
            player.setItemInHand(InteractionHand.MAIN_HAND, stack1);
            stack1 = stack;
        }
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (stack0 != ItemStack.EMPTY) {
            view.store("stack0", ItemStack.CODEC, stack0);
        }
        if (stack1 != ItemStack.EMPTY) {
            view.store("stack1", ItemStack.CODEC, stack1);
        }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        Optional<ItemStack> s0 = view.read("stack0", ItemStack.CODEC);
        s0.ifPresent(stack -> this.stack0 = stack);
        Optional<ItemStack> s1 = view.read("stack1", ItemStack.CODEC);
        s1.ifPresent(stack -> this.stack1 = stack);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    public void clear(){
        this.stack0 = ItemStack.EMPTY;
        this.stack1 = ItemStack.EMPTY;
    }

    public boolean isEmpty(){
        return stack0.isEmpty() && stack1.isEmpty();
    }
}