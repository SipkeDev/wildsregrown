package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
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
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (stack != ItemStack.EMPTY) {
            view.store("item", ItemStack.CODEC, stack);
        }
        view.putInt("count", count);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        Optional<ItemStack> optional = view.read("item", ItemStack.CODEC);
        if (optional.isPresent()) {
            this.stack = optional.get();
            this.count = view.getIntOr("count", 0);
        }
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