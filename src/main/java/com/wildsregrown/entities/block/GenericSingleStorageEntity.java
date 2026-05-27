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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class GenericSingleStorageEntity extends BlockEntity {

    private ItemStack stack;

    public GenericSingleStorageEntity(BlockPos pos, BlockState state) {
        super(ModEntities.genericSingleStorageEntity, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack(){
        return stack;
    }

    public void setStack(ItemStack stack){
        this.stack = stack;
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (stack != ItemStack.EMPTY) {
            view.store("item", ItemStack.CODEC, stack);
        }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        Optional<ItemStack> s0 = view.read("item", ItemStack.CODEC);
        s0.ifPresent(stack -> this.stack = stack);
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

    public boolean isEmpty(){
        return stack.isEmpty() || stack == ItemStack.EMPTY;
    }
}