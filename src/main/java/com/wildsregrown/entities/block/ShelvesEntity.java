package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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

    public void swapStack(int flag, PlayerEntity player){
        if (flag == 0){
            ItemStack stack = player.getMainHandStack();
            player.setStackInHand(Hand.MAIN_HAND, stack0);
            stack0 = stack;
        }
        if (flag == 1){
            ItemStack stack = player.getMainHandStack();
            player.setStackInHand(Hand.MAIN_HAND, stack1);
            stack1 = stack;
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (stack0 != ItemStack.EMPTY) {
            view.put("stack0", ItemStack.CODEC, stack0);
        }
        if (stack1 != ItemStack.EMPTY) {
            view.put("stack1", ItemStack.CODEC, stack1);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<ItemStack> s0 = view.read("stack0", ItemStack.CODEC);
        s0.ifPresent(stack -> this.stack0 = stack);
        Optional<ItemStack> s1 = view.read("stack1", ItemStack.CODEC);
        s1.ifPresent(stack -> this.stack1 = stack);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public void clear(){
        this.stack0 = ItemStack.EMPTY;
        this.stack1 = ItemStack.EMPTY;
    }

    public boolean isEmpty(){
        return stack0.isEmpty() && stack1.isEmpty();
    }
}