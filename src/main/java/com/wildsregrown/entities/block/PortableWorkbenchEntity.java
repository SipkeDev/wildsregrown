package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

public class PortableWorkbenchEntity extends BlockEntity {

    private ItemStack stack;

    public PortableWorkbenchEntity(BlockPos pos, BlockState state) {
        super(ModEntities.portableWorkbenchEntity, pos, state);
        this.stack = ItemStack.EMPTY;
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
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<ItemStack> s = view.read("item", ItemStack.CODEC);
        s.ifPresent(stack -> this.stack = stack);
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

    public boolean isEmpty(){
        return stack == ItemStack.EMPTY;
    }

}