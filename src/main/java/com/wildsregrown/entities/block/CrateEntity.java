package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrateEntity extends BlockEntity {

    private ItemStack stack;
    public int count;
    private final int maxCount;

    public CrateEntity(BlockPos pos, BlockState state) {
        super(ModEntities.crateEntity, pos, state);
        this.stack = ItemStack.EMPTY;
        this.count = 0;
        this.maxCount = 1024;
    }

    public ItemStack getStack(){
        return stack;
    }

    public void setStack(ItemStack stack){
        this.stack = stack;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (stack != ItemStack.EMPTY) {
            view.put("item", ItemStack.CODEC, stack);
        }
        view.putInt("count", count);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<ItemStack> optional = view.read("item", ItemStack.CODEC);
        if (optional.isPresent()) {
            this.stack = optional.get();
            this.count = view.getInt("count", 0);
        }
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

    public boolean store(ItemStack stack){

        if (count >= maxCount){return false;}

        if (!isEmpty()){
            if (stack.getItem() == this.stack.getItem()){
                count += stack.getCount();
                return true;
            }
        }else {
            this.stack = stack;
            count = stack.getCount();
            return true;
        }
        return false;
    }

    public ItemStack retrieve(){
        if (count <= 64){
            int c = count;
            ItemStack stack1 = this.stack.copyWithCount(c);
            clear();
            return stack1;
        }else {
            count -= 64;
            return this.stack.copyWithCount(64);
        }
    }

    public void clear(){
        this.stack = ItemStack.EMPTY;
        this.count = 0;
    }

    public boolean isEmpty(){
        return stack.isEmpty() || stack == ItemStack.EMPTY;
    }

    public int getMaxCount() {
        return maxCount;
    }
}