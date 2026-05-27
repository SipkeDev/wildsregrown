package com.wildsregrown.entities.block;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class GenericSmallStorageEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;
    private final String name;

    public GenericSmallStorageEntity(BlockPos pos, BlockState state) {
        super(ModEntities.genericSmallStorage, pos, state);
        this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerStateManager();
        this.name = "Storage";
    }

    public GenericSmallStorageEntity(BlockPos pos, BlockState state, String name) {
        super(ModEntities.genericSmallStorage, pos, state);
        this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerStateManager();
        this.name = name;
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, this.inventory);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        ContainerHelper.loadAllItems(view, this.inventory);
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

    @Override
    protected Component getDefaultName() {
        return Component.literal(name);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return ChestMenu.threeRows(syncId, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    public void onOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState(), 2f);
        }
    }

    public void onClose(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void updateViewerCount(Level world, BlockPos pos, BlockState state){
        if (!this.remove) {
            this.stateManager.recheckOpeners(world, pos, state);
        }
    }

    private class ContainerStateManager extends ContainerOpenersCounter{

        @Override
        protected void onOpen(Level world, BlockPos pos, BlockState state) {
            this.playSound(state, SoundEvents.CHEST_OPEN);
            this.setOpen(state, true);
        }

        @Override
        protected void onClose(Level world, BlockPos pos, BlockState state) {
            this.playSound(state, SoundEvents.CHEST_CLOSE);
            this.setOpen(state, false);
        }

        @Override
        protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {}

        @Override
        public boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu screenHandler){
                WildsRegrown.LOGGER.info(String.valueOf(screenHandler.getContainer() == GenericSmallStorageEntity.this));
                return screenHandler.getContainer() == GenericSmallStorageEntity.this;
            }
            return false;
        }

        void setOpen(BlockState state, boolean open) {
            level.setBlock(getBlockPos(), state.setValue(BlockStateProperties.OPEN, open), 3);
        }

        void playSound(BlockState state, SoundEvent soundEvent) {
            Vec3i vec3i = (state.getValue(BlockStateProperties.HORIZONTAL_FACING)).getUnitVec3i();
            double d = worldPosition.getX() + 0.5F + vec3i.getX() / 2.0F;
            double e = worldPosition.getY() + 0.5F + vec3i.getY() / 2.0F;
            double f = worldPosition.getZ() + 0.5F + vec3i.getZ() / 2.0F;
            level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 1.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }

    }

}