package com.wildsregrown.entities.block;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GenericSmallStorageEntity extends LootableContainerBlockEntity {

    private DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager;
    private final String name;

    public GenericSmallStorageEntity(BlockPos pos, BlockState state) {
        super(ModEntities.genericSmallStorage, pos, state);
        this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerStateManager();
        this.name = "Storage";
    }

    public GenericSmallStorageEntity(BlockPos pos, BlockState state, String name) {
        super(ModEntities.genericSmallStorage, pos, state);
        this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerStateManager();
        this.name = name;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, this.inventory);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, this.inventory);
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

    @Override
    protected Text getContainerName() {
        return Text.literal(name);
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 27;
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState(), 2f);
        }
    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void updateViewerCount(World world, BlockPos pos, BlockState state){
        if (!this.removed) {
            this.stateManager.updateViewerCount(world, pos, state);
        }
    }

    private class ContainerStateManager extends ViewerCountManager{

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            this.playSound(state, SoundEvents.BLOCK_CHEST_OPEN);
            this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            this.playSound(state, SoundEvents.BLOCK_CHEST_CLOSE);
            this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {}

        @Override
        public boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler screenHandler){
                WildsRegrown.LOGGER.info(String.valueOf(screenHandler.getInventory() == GenericSmallStorageEntity.this));
                return screenHandler.getInventory() == GenericSmallStorageEntity.this;
            }
            return false;
        }

        void setOpen(BlockState state, boolean open) {
            world.setBlockState(getPos(), state.with(Properties.OPEN, open), 3);
        }

        void playSound(BlockState state, SoundEvent soundEvent) {
            Vec3i vec3i = (state.get(Properties.HORIZONTAL_FACING)).getVector();
            double d = pos.getX() + 0.5F + vec3i.getX() / 2.0F;
            double e = pos.getY() + 0.5F + vec3i.getY() / 2.0F;
            double f = pos.getZ() + 0.5F + vec3i.getZ() / 2.0F;
            world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

    }

}