package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemLootPedestalEntity extends BlockEntity {

    private ItemStack stack;

    public ItemLootPedestalEntity(BlockPos pos, BlockState state) {
        super(ModEntities.itemLootPedestal, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void takeItem(PlayerEntity player){
        player.setStackInHand(Hand.MAIN_HAND, stack);
        clear();
        markDirty();
        playSound(player.getEntityWorld(), player.getBlockPos(), SoundEvents.ENTITY_PLAYER_LEVELUP);
    }

    public void placeItem(PlayerEntity player){
        this.stack = player.getMainHandStack();
        player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        markDirty();
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!stack.isEmpty()) {
            view.put("stack", ItemStack.CODEC, stack);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<ItemStack> s0 = view.read("stack", ItemStack.CODEC);
        s0.ifPresent(stack -> this.stack = stack);
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
        this.stack = ItemStack.EMPTY;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, ItemLootPedestalEntity entity) {

        if (!world.isClient() && state.get(Properties.ENABLED)) {

            double effectRadius = 32;
            Box box = (new Box(pos)).expand(effectRadius).stretch(0.0F, world.getHeight(),0.0F);

            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            for(PlayerEntity playerEntity : list) {
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 12, 0, true, true));
            }

        }

    }

    public static void playSound(World world, BlockPos pos, SoundEvent sound) {
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

}